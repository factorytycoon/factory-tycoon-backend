package com.factory.tycoon.equipment.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EquipmentRequest {
    private Long factoryId;
    private String name;
    private String status;
    private String type;
    private LocalDateTime installedAt;
    private String location;
    private String description;
    private String modeling;
}
