package com.factory.tycoon.sensoranalysis.domain.dto;

import java.math.BigDecimal;

import com.factory.tycoon.sensoranalysis.domain.entity.SensorAnalysisEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SensorAnalysisResponse {
    private Long sensorAnalysisId;
    private BigDecimal maxValue;
    private BigDecimal minValue;
    private BigDecimal avgValue;

    public SensorAnalysisResponse(SensorAnalysisEntity sensorAnalysis) {
        this.sensorAnalysisId = sensorAnalysis.getSensorAnalysisId();
        this.maxValue = sensorAnalysis.getMaxValue();
        this.minValue = sensorAnalysis.getMinValue();
        this.avgValue = sensorAnalysis.getAvgValue();
    }
}
