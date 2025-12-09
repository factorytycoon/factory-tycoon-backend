package com.factory.tycoon.sensoranalysis.domain.dto;

import com.factory.tycoon.sensoranalysis.domain.entity.SensorAnalysisEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class SensorAnalysisResponse {
    private Long sensorAnalysisId;
    private Long sensorDataId;
    private BigDecimal maxValue;
    private BigDecimal minValue;
    private BigDecimal avgValue;

    public SensorAnalysisResponse(SensorAnalysisEntity sensorAnalysis) {
        this.sensorAnalysisId = sensorAnalysis.getSensorAnalysisId();
        this.sensorDataId = sensorAnalysis.getSensorData().getSensorDataId();
        this.maxValue = sensorAnalysis.getMaxValue();
        this.minValue = sensorAnalysis.getMinValue();
        this.avgValue = sensorAnalysis.getAvgValue();
    }
}
