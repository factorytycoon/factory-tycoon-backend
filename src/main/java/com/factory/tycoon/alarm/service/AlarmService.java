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

    public List<AlarmResponse> getAllAlarms(Long factoryId, String triggerName, String status) {
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
        
        // triggerName으로 단계적 필터링
        if (triggerName != null) {
            alarms = alarms.stream()
                    .filter(alarm -> alarm.getTriggerName() != null && alarm.getTriggerName().equals(triggerName))
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
            
            // 3. hits 배열을 JSON 문자열로 변환
            String sensorSnapshot = objectMapper.writeValueAsString(request.getHits());
            
            // 4. AlarmEntity 생성 및 저장
            AlarmEntity alarm = AlarmEntity.builder()
                    .equipmentId(equipment.getEquipmentId())
                    .monitorName(request.getMonitor_name())
                    .triggerName(request.getTrigger_name())
                    .sensorSnapshot(sensorSnapshot)
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
}
