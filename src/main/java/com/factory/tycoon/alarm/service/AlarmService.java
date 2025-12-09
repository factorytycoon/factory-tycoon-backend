package com.factory.tycoon.alarm.service;

import com.factory.tycoon.alarm.domain.dto.AlarmRequest;
import com.factory.tycoon.alarm.domain.dto.AlarmResponse;
import com.factory.tycoon.alarm.domain.entity.AlarmEntity;
import com.factory.tycoon.alarm.domain.entity.AlarmLevel;
import com.factory.tycoon.alarm.repository.AlarmRepository;
import com.factory.tycoon.sensor.domain.entity.SensorEntity;
import com.factory.tycoon.sensor.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final SensorRepository sensorRepository;

    public List<AlarmResponse> getAllAlarms(String level, Boolean status) {
        if (level != null && status != null) {
            AlarmLevel alarmLevel = AlarmLevel.from(level);
            return alarmRepository.findAll().stream()
                    .filter(alarm -> alarm.getLevel() == alarmLevel && alarm.getStatus().equals(status))
                    .map(AlarmResponse::new)
                    .collect(Collectors.toList());
        } else if (level != null) {
            return alarmRepository.findByLevel(AlarmLevel.from(level)).stream()
                    .map(AlarmResponse::new)
                    .collect(Collectors.toList());
        } else if (status != null) {
            return alarmRepository.findByStatus(status).stream()
                    .map(AlarmResponse::new)
                    .collect(Collectors.toList());
        }
        return alarmRepository.findAll().stream()
                .map(AlarmResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public AlarmResponse createAlarm(AlarmRequest request) {
        SensorEntity sensor = sensorRepository.findById(request.getSensorId())
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found with id: " + request.getSensorId()));

        AlarmEntity alarm = AlarmEntity.builder()
                .sensor(sensor)
                .level(AlarmLevel.from(request.getLevel()))
                .message(request.getMessage())
                .status(request.getStatus() != null ? request.getStatus() : false)
                .build();
        AlarmEntity saved = alarmRepository.save(alarm);
        return new AlarmResponse(saved);
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

    public List<AlarmResponse> getAlarmsBySensorId(Long sensorId, String level, Boolean status) {
        if (level != null && status != null) {
            return alarmRepository.findBySensor_SensorIdAndLevel(sensorId, AlarmLevel.from(level)).stream()
                    .filter(alarm -> alarm.getStatus().equals(status))
                    .map(AlarmResponse::new)
                    .collect(Collectors.toList());
        } else if (level != null) {
            return alarmRepository.findBySensor_SensorIdAndLevel(sensorId, AlarmLevel.from(level)).stream()
                    .map(AlarmResponse::new)
                    .collect(Collectors.toList());
        } else if (status != null) {
            return alarmRepository.findBySensor_SensorIdAndStatus(sensorId, status).stream()
                    .map(AlarmResponse::new)
                    .collect(Collectors.toList());
        }
        return alarmRepository.findBySensor_SensorId(sensorId).stream()
                .map(AlarmResponse::new)
                .collect(Collectors.toList());
    }
}
