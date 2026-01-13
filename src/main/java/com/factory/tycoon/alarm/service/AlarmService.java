package com.factory.tycoon.alarm.service;

import com.factory.tycoon.alarm.domain.dto.AlarmOsRequest;
import com.factory.tycoon.alarm.domain.dto.AlarmRequest;
import com.factory.tycoon.alarm.domain.dto.AlarmResponse;
import com.factory.tycoon.alarm.domain.entity.AlarmEntity;
import com.factory.tycoon.alarm.repository.AlarmRepository;
import com.factory.tycoon.equipment.domain.entity.EquipmentEntity;
import com.factory.tycoon.equipment.repository.EquipmentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final EquipmentRepository equipmentRepository;
    private final ObjectMapper objectMapper;

    public List<AlarmResponse> getAllAlarms(Long factoryId, String level, String status) {
        List<AlarmEntity> alarms = new java.util.ArrayList<>();

        // factoryId가 있으면 해당 공장의 설비 ID를 먼저 조회
        if (factoryId != null) {
            List<Long> equipmentIds = equipmentRepository.findByFactory_FactoryId(factoryId)
                    .stream()
                    .map(EquipmentEntity::getEquipmentId)
                    .collect(Collectors.toList());

            if (equipmentIds.isEmpty()) {
                return List.of();
            }

            alarms = alarmRepository.findByEquipmentIdIn(equipmentIds);
        } else {
            // factoryId가 없으면 전체 조회
            alarms = alarmRepository.findAll();
        }

        // status로 단계적 필터링
        if (status != null) {
            alarms = alarms.stream()
                    .filter(alarm -> alarm.getStatus().equals(status))
                    .collect(Collectors.toList());
        }

        // level으로 단계적 필터링 (중복되는 triggerName대신 level 사용)
        if (level != null) {
            alarms = alarms.stream()
                    .filter(alarm -> alarm.getLevel() != null && alarm.getLevel().equals(level))
                    .collect(Collectors.toList());
        }

        return alarms.stream()
                .map(AlarmResponse::new)
                .collect(Collectors.toList());
    }

    public List<AlarmResponse> getAlarmsByEquipmentId(Long equipmentId) {
        return alarmRepository.findByEquipmentId(equipmentId).stream()
                .map(AlarmResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public AlarmResponse createAlarm(AlarmRequest request) {
        String title = request.getTitle();
        if (title == null || title.isBlank()) {
            title = request.getLevel() != null ? request.getLevel() : "alarm";
        }

        AlarmEntity alarm = AlarmEntity.builder()
                .equipmentId(request.getEquipmentId())
                .title(title)
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : "OPEN")
                .level(request.getLevel())
                .sensorDt(request.getSensorDt())
                .build();
        AlarmEntity saved = alarmRepository.save(alarm);
        return new AlarmResponse(saved);
    }

    @Transactional
    public AlarmResponse createOsAlarm(AlarmOsRequest request) {
        try {
            // 1. device_id로 equipment 조회
            if (request.getHits() == null || request.getHits().isEmpty()) {
                throw new IllegalArgumentException("No hits data in OpenSearch alarm");
            }

            AlarmOsRequest.Hit firstHit = request.getHits().get(0);
            String deviceId = firstHit.getDevice_id();

            EquipmentEntity equipment = equipmentRepository.findByName(deviceId)
                    .orElseThrow(() -> new IllegalArgumentException("Equipment not found with name: " + deviceId));

            // 2. sensor_time 파싱
            LocalDateTime sensorDt = null;
            if (firstHit.getSensor_time() != null) {
                try {
                    sensorDt = OffsetDateTime.parse(firstHit.getSensor_time()).toLocalDateTime();
                } catch (Exception e) {
                    log.warn("Failed to parse sensor_time: {}", firstHit.getSensor_time(), e);
                }
            }

            String title = request.getMonitor_name() != null ? request.getMonitor_name() : request.getTrigger_name();
            if (title == null || title.isBlank()) {
                title = "alarm";
            }

            String description = request.getMonitor_name() + ": " + request.getTrigger_name();
            if (request.getHits() != null && !request.getHits().isEmpty()) {
                try {
                    description += "\nDetails: " + objectMapper.writeValueAsString(request.getHits());
                } catch (Exception e) {
                    log.warn("hits stringify failed", e);
                }
            }

            AlarmEntity alarm = AlarmEntity.builder()
                    .equipmentId(equipment.getEquipmentId())
                    .title(title)
                    .description(description)
                    .level(request.getTrigger_name())
                    .status("OPEN")
                    .sensorDt(sensorDt)
                    .build();

            AlarmEntity saved = alarmRepository.save(alarm);
            log.info("OpenSearch alarm saved: alarmId={}, equipmentId={}, deviceId={}",
                    saved.getAlarmId(), saved.getEquipmentId(), deviceId);

            return new AlarmResponse(saved);
        } catch (Exception e) {
            log.error("Failed to create OpenSearch alarm", e);
            throw new RuntimeException("Failed to create OpenSearch alarm: " + e.getMessage(), e);
        }
    }

    public AlarmResponse getAlarm(Long id) {
        AlarmEntity alarm = alarmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alarm not found with id: " + id));
        return new AlarmResponse(alarm);
    }

    @Transactional
    public void deleteAlarm(Long id) {
        AlarmEntity alarm = alarmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alarm not found with id: " + id));
        alarmRepository.delete(alarm);
    }

    @Transactional
    public AlarmResponse resolveAlarm(Long alarmId) {
        AlarmEntity alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new IllegalArgumentException("Alarm not found with id: " + alarmId));

        // 알람 상태를 CLOSE로 업데이트
        alarm.update(alarm.getDescription(), "CLOSE", alarm.getLevel());

        // 해당 equipment의 상태를 normal로 업데이트
        EquipmentEntity equipment = equipmentRepository.findById(alarm.getEquipmentId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Equipment not found with id: " + alarm.getEquipmentId()));

        equipment.update(equipment.getName(), "normal", equipment.getType(),
                equipment.getInstalledAt(), equipment.getLocation(),
                equipment.getDescription(), equipment.getModeling());

        return new AlarmResponse(alarm);
    }
}
