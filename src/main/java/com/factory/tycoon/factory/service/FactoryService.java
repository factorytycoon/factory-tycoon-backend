package com.factory.tycoon.factory.service;

import com.factory.tycoon.equipment.domain.dto.EquipmentResponse;
import com.factory.tycoon.equipment.repository.EquipmentRepository;
import com.factory.tycoon.factory.domain.dto.FactoryRequest;
import com.factory.tycoon.factory.domain.dto.FactoryResponse;
import com.factory.tycoon.factory.domain.entity.FactoryEntity;
import com.factory.tycoon.factory.repository.FactoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FactoryService {

    private final FactoryRepository factoryRepository;
    private final EquipmentRepository equipmentRepository;

    public List<FactoryResponse> getFactories() {
        return factoryRepository.findAll().stream()
                .map(FactoryResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public FactoryResponse createFactory(FactoryRequest request) {
        FactoryEntity factory = FactoryEntity.builder()
                .name(request.getName())
                .location(request.getLocation())
                .description(request.getDescription())
                .phone(request.getPhone())
                .build();
        FactoryEntity savedFactory = factoryRepository.save(factory);
        return new FactoryResponse(savedFactory);
    }

    public FactoryResponse getFactory(Long id) {
        FactoryEntity factory = factoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Factory not found with id: " + id));
        return new FactoryResponse(factory);
    }

    @Transactional
    public FactoryResponse updateFactory(Long id, FactoryRequest request) {
        FactoryEntity factory = factoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Factory not found with id: " + id));
        factory.update(request.getName(), request.getLocation(), request.getDescription(), request.getPhone());
        return new FactoryResponse(factory);
    }

    @Transactional
    public void deleteFactory(Long id) {
        FactoryEntity factory = factoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Factory not found with id: " + id));
        factoryRepository.delete(factory);
    }

    public List<Object> getEquipments(Long factoryId) {
        return equipmentRepository.findByFactory_FactoryId(factoryId).stream()
                .map(EquipmentResponse::new)
                .collect(Collectors.toList());
    }
}
