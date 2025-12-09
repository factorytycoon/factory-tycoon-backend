package com.factory.tycoon.sensor.service;

import com.factory.tycoon.equipment.domain.entity.EquipmentEntity;
import com.factory.tycoon.equipment.repository.EquipmentRepository;
import com.factory.tycoon.sensor.domain.dto.SensorRequest;
import com.factory.tycoon.sensor.domain.dto.SensorResponse;
import com.factory.tycoon.sensor.domain.entity.SensorEntity;
import com.factory.tycoon.sensor.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;
    private final EquipmentRepository equipmentRepository;

    public List<SensorResponse> getSensors() {
        return sensorRepository.findAll().stream()
                .map(SensorResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public SensorResponse createSensor(SensorRequest request) {
        EquipmentEntity equipment = equipmentRepository.findById(request.getEquipmentId())
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found with id: " + request.getEquipmentId()));

        SensorEntity sensor = SensorEntity.builder()
                .equipment(equipment)
                .name(request.getName())
                .type(request.getType())
                .build();
        SensorEntity saved = sensorRepository.save(sensor);
        return new SensorResponse(saved);
    }

    public SensorResponse getSensor(Long id) {
        SensorEntity sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found with id: " + id));
        return new SensorResponse(sensor);
    }

    @Transactional
    public SensorResponse updateSensor(Long id, SensorRequest request) {
        SensorEntity sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found with id: " + id));
        sensor.update(request.getName(), request.getType());
        return new SensorResponse(sensor);
    }

    @Transactional
    public void deleteSensor(Long id) {
        SensorEntity sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found with id: " + id));
        sensorRepository.delete(sensor);
    }

    public List<SensorResponse> getSensorsByEquipmentId(Long equipmentId) {
        return sensorRepository.findByEquipment_EquipmentId(equipmentId).stream()
                .map(SensorResponse::new)
                .collect(Collectors.toList());
    }

    public List<Object> getAlarms(Long sensorId) {
        sensorRepository.findById(sensorId)
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found with id: " + sensorId));
        // TODO: replace with real alarm data
        return List.of();
    }

    public List<Object> getSensorData(Long sensorId, LocalDate date, String sensorType) {
        sensorRepository.findById(sensorId)
                .orElseThrow(() -> new IllegalArgumentException("Sensor not found with id: " + sensorId));
        // TODO: replace with real sensor data
        return List.of();
    }

    public List<SensorResponse> getSensorsByType(String sensorType) {
        return sensorRepository.findByType(sensorType).stream()
                .map(SensorResponse::new)
                .collect(Collectors.toList());
    }
}
