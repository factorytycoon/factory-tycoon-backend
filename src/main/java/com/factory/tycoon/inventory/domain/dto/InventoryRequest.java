package com.factory.tycoon.inventory.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InventoryRequest {
    private Long factoryId;
    private String itemName;
    private Integer quantity;
    private String unit;
}
