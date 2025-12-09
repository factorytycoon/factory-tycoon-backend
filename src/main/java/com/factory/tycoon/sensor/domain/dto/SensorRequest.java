package com.factory.tycoon.sensor.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SensorRequest {
    private Long equipmentId;
    private String name;
    private String type;
}
