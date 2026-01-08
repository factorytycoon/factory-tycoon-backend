package com.factory.tycoon.equipment.domain.dto;

import com.factory.tycoon.equipment.domain.entity.EquipmentEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EquipmentResponse {
    private Long equipmentId;
    private Long factoryId;
    private String name;
    private String status;
    private String type;
    private LocalDateTime installedAt;
    private LocalDateTime createdAt;
    private String modeling;

    public EquipmentResponse(EquipmentEntity equipment) {
        this.equipmentId = equipment.getEquipmentId();
        this.factoryId = equipment.getFactory().getFactoryId();
        this.name = equipment.getName();
        this.status = equipment.getStatus() != null ? equipment.getStatus().name().toLowerCase() : null;
        this.type = equipment.getType();
        this.installedAt = equipment.getInstalledAt();
        this.createdAt = equipment.getCreatedAt();
        this.modeling = equipment.getModeling();
    }
}
