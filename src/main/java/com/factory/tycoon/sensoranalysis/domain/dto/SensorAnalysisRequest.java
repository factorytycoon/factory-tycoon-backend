package com.factory.tycoon.sensoranalysis.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class SensorAnalysisRequest {
    private Long sensorDataId;
    private BigDecimal maxValue;
    private BigDecimal minValue;
    private BigDecimal avgValue;
}
