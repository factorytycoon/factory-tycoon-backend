package com.factory.tycoon.inventory.service;

import com.factory.tycoon.factory.domain.entity.FactoryEntity;
import com.factory.tycoon.factory.repository.FactoryRepository;
import com.factory.tycoon.inventory.domain.dto.InventoryRequest;
import com.factory.tycoon.inventory.domain.dto.InventoryResponse;
import com.factory.tycoon.inventory.domain.entity.InventoryEntity;
import com.factory.tycoon.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final FactoryRepository factoryRepository;

    public List<InventoryResponse> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .map(InventoryResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public InventoryResponse createInventory(InventoryRequest request) {
        FactoryEntity factory = factoryRepository.findById(request.getFactoryId())
                .orElseThrow(() -> new IllegalArgumentException("Factory not found with id: " + request.getFactoryId()));

        InventoryEntity inventory = InventoryEntity.builder()
                .factory(factory)
                .itemName(request.getItemName())
                .quantity(request.getQuantity())
                .unit(request.getUnit())
                .build();
        InventoryEntity saved = inventoryRepository.save(inventory);
        return new InventoryResponse(saved);
    }

    public InventoryResponse getInventory(Long id) {
        InventoryEntity inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found with id: " + id));
        return new InventoryResponse(inventory);
    }

    @Transactional
    public InventoryResponse updateInventory(Long id, InventoryRequest request) {
        InventoryEntity inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found with id: " + id));
        inventory.update(request.getItemName(), request.getQuantity(), request.getUnit());
        return new InventoryResponse(inventory);
    }

    @Transactional
    public void deleteInventory(Long id) {
        InventoryEntity inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found with id: " + id));
        inventoryRepository.delete(inventory);
    }

    public List<InventoryResponse> getInventoryByFactoryId(Long factoryId) {
        return inventoryRepository.findByFactory_FactoryId(factoryId).stream()
                .map(InventoryResponse::new)
                .collect(Collectors.toList());
    }
}
