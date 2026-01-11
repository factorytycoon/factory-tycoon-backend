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
    private String key;
    private String label;
    private String unit;
    private LocalDateTime createdAt;

    public SensorResponse(SensorEntity sensor) {
        this.sensorId = sensor.getSensorId();
        this.equipmentId = sensor.getEquipment().getEquipmentId();
        this.key = sensor.getKey();
        this.label = sensor.getLabel();
        this.unit = sensor.getUnit();
        this.createdAt = sensor.getCreatedAt();
    }
}
