package com.factory.tycoon.sensoranalysis.domain.entity;

import com.factory.tycoon.sensor.domain.entity.SensorEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sensor_analysis")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SensorAnalysisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sensorAnalysisId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id")
    private SensorEntity sensor;

    private LocalDate date;

    private BigDecimal avgValue;
    private BigDecimal maxValue;
    private BigDecimal minValue;

    @Builder
    public SensorAnalysisEntity(SensorEntity sensor, LocalDate date, BigDecimal avgValue, BigDecimal maxValue, BigDecimal minValue) {
        this.sensor = sensor;
        this.date = date;
        this.avgValue = avgValue;
        this.maxValue = maxValue;
        this.minValue = minValue;
    }
}