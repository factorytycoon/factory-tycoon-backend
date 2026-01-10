package com.factory.tycoon.factory.service;

import com.factory.tycoon.equipment.domain.dto.EquipmentResponse;
import com.factory.tycoon.equipment.service.EquipmentService;
import com.factory.tycoon.factory.domain.dto.FactoryRequest;
import com.factory.tycoon.factory.domain.dto.FactoryResponse;
import com.factory.tycoon.factory.domain.entity.FactoryEntity;
import com.factory.tycoon.factory.repository.FactoryRepository;
import com.factory.tycoon.inventory.domain.dto.InventoryResponse;
import com.factory.tycoon.inventory.service.InventoryService;
import com.factory.tycoon.order.domain.dto.OrderResponse;
import com.factory.tycoon.order.service.OrderService;
import com.factory.tycoon.prediction.domain.dto.PredictionResponse;
import com.factory.tycoon.prediction.service.PredictionService;
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
    private final EquipmentService equipmentService;
    private final InventoryService inventoryService;
    private final OrderService orderService;
    private final PredictionService predictionService;

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

    public List<EquipmentResponse> getEquipments(Long factoryId) {
        return equipmentService.getEquipmentsByFactoryId(factoryId);
    }

    public List<InventoryResponse> getInventory(Long factoryId) {
        return inventoryService.getInventoryByFactoryId(factoryId);
    }

    public List<OrderResponse> getOrders(Long factoryId) {
        return orderService.getOrdersByFactoryId(factoryId);
    }

    // public List<PredictionResponse> getPredictions(Long factoryId, String type, String level, Boolean selected) {
    //     return predictionService.getPredictionsByFactory(factoryId, type, level, selected);
    // }

    @Transactional
    public FactoryResponse updateFactoryModeling(Long factoryId, String modeling) {
        FactoryEntity factory = factoryRepository.findById(factoryId)
                .orElseThrow(() -> new IllegalArgumentException("Factory not found with id: " + factoryId));
        factory.updateModeling(modeling);
        return new FactoryResponse(factory);
    }
}
