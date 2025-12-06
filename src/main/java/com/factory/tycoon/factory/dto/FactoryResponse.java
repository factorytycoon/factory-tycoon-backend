package com.factory.tycoon.factory.dto;

import com.factory.tycoon.factory.domain.Factory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FactoryResponse {
    private Long factoryId;
    private String name;
    private String location;
    private String description;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public FactoryResponse(Factory factory) {
        this.factoryId = factory.getFactoryId();
        this.name = factory.getName();
        this.location = factory.getLocation();
        this.description = factory.getDescription();
        this.phone = factory.getPhone();
        this.createdAt = factory.getCreatedAt();
        this.updatedAt = factory.getUpdatedAt();
    }
}
