package com.factory.tycoon.sensoranalysis.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class SensorAnalysisRequest {
    private Long sensorDataId;
    private LocalDate date;
    private List<BigDecimal> sensorValues;

}
