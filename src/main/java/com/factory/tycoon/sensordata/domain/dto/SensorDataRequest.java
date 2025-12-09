package com.factory.tycoon.sensordata.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class SensorDataRequest {
    private Long sensorId;
    private String path;
    private LocalDate date;
}
