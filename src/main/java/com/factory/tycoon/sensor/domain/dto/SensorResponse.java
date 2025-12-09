package com.factory.tycoon.sensor.domain.dto;

import com.factory.tycoon.sensor.domain.entity.SensorEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SensorResponse {
    private Long sensorId;
    private Long equipmentId;
    private String name;
    private String type;
    private LocalDateTime createdAt;

    public SensorResponse(SensorEntity sensor) {
        this.sensorId = sensor.getSensorId();
        this.equipmentId = sensor.getEquipment().getEquipmentId();
        this.name = sensor.getName();
        this.type = sensor.getType();
        this.createdAt = sensor.getCreatedAt();
    }
}
