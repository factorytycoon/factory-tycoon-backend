package com.factory.tycoon.sensoranalysis.domain.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class SensorAnalysisRequest {
    private Long sensorDataId;
    private LocalDate date;
}
