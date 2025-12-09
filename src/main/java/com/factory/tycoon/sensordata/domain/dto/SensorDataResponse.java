package com.factory.tycoon.sensordata.domain.dto;

import com.factory.tycoon.sensordata.domain.entity.SensorDataEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SensorDataResponse {
    private Long sensorDataId;
    private Long sensorId;
    private String path;
    private LocalDate date;
    private LocalDateTime createdAt;

    public SensorDataResponse(SensorDataEntity sensorData) {
        this.sensorDataId = sensorData.getSensorDataId();
        this.sensorId = sensorData.getSensor().getSensorId();
        this.path = sensorData.getPath();
        this.date = sensorData.getDate();
        this.createdAt = sensorData.getCreatedAt();
    }
}
