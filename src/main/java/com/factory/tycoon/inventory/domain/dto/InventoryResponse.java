package com.factory.tycoon.inventory.domain.dto;

import com.factory.tycoon.inventory.domain.entity.InventoryEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class InventoryResponse {
    private Long inventoryId;
    private Long factoryId;
    private String itemName;
    private Integer quantity;
    private String unit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate expirationDate;

    public InventoryResponse(InventoryEntity inventory) {
        this.inventoryId = inventory.getInventoryId();
        this.factoryId = inventory.getFactory().getFactoryId();
        this.itemName = inventory.getItemName();
        this.quantity = inventory.getQuantity();
        this.unit = inventory.getUnit();
        this.createdAt = inventory.getCreatedAt();
        this.updatedAt = inventory.getUpdatedAt();
        this.expirationDate = inventory.getExpirationDate();
    }
}
