package com.factory.tycoon.factory.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.factory.tycoon.factory.domain.entity.FactoryEntity;

@Getter
@NoArgsConstructor
public class FactoryResponse {
    private Long factoryId;
    private String name;
    private String location;
    private String description;
    private String phone;
    private String modeling;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public FactoryResponse(FactoryEntity factory) {
        this.factoryId = factory.getFactoryId();
        this.name = factory.getName();
        this.location = factory.getLocation();
        this.description = factory.getDescription();
        this.phone = factory.getPhone();
        this.modeling = factory.getModeling();
        this.createdAt = factory.getCreatedAt();
        this.updatedAt = factory.getUpdatedAt();
    }
}
