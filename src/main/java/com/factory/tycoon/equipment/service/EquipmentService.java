package com.factory.tycoon.equipment.service;

import com.factory.tycoon.equipment.domain.dto.EquipmentRequest;
import com.factory.tycoon.equipment.domain.dto.EquipmentResponse;
import com.factory.tycoon.equipment.domain.entity.EquipmentEntity;
import com.factory.tycoon.equipment.domain.entity.EquipmentStatus;
import com.factory.tycoon.equipment.repository.EquipmentRepository;
import com.factory.tycoon.factory.domain.entity.FactoryEntity;
import com.factory.tycoon.factory.repository.FactoryRepository;
import com.factory.tycoon.sensor.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final FactoryRepository factoryRepository;
    private final SensorService sensorService;

    public List<EquipmentResponse> getEquipments() {
        return equipmentRepository.findAll().stream()
                .map(EquipmentResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public EquipmentResponse createEquipment(EquipmentRequest request) {
        FactoryEntity factory = factoryRepository.findById(request.getFactoryId())
                .orElseThrow(() -> new IllegalArgumentException("Factory not found with id: " + request.getFactoryId()));

        EquipmentEntity equipment = EquipmentEntity.builder()
            .factory(factory)
            .name(request.getName())
            .status(EquipmentStatus.from(request.getStatus()))
            .type(request.getType())
            .installedAt(request.getInstalledAt())
            .build();
        EquipmentEntity savedEquipment = equipmentRepository.save(equipment);
        return new EquipmentResponse(savedEquipment);
    }

    public EquipmentResponse getEquipment(Long id) {
        EquipmentEntity equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found with id: " + id));
        return new EquipmentResponse(equipment);
    }

    @Transactional
    public EquipmentResponse updateEquipment(Long id, EquipmentRequest request) {
        EquipmentEntity equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found with id: " + id));
        equipment.update(request.getName(), EquipmentStatus.from(request.getStatus()), request.getType(), request.getInstalledAt());
        return new EquipmentResponse(equipment);
    }

    @Transactional
    public void deleteEquipment(Long id) {
        EquipmentEntity equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found with id: " + id));
        equipmentRepository.delete(equipment);
    }

    public List<EquipmentResponse> getEquipmentsByFactoryId(Long factoryId) {
        return equipmentRepository.findByFactory_FactoryId(factoryId).stream()
                .map(EquipmentResponse::new)
                .collect(Collectors.toList());
    }

    public List<Object> getSensors(Long equipmentId) {
        equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found with id: " + equipmentId));
        
        return sensorService.getSensorsByEquipmentId(equipmentId).stream()
                .map(Object.class::cast)
                .collect(Collectors.toList());
    }
}
