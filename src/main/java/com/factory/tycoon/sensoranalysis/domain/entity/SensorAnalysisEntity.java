package com.factory.tycoon.sensoranalysis.domain.entity;

import com.factory.tycoon.sensordata.domain.entity.SensorDataEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "sensor_analysis")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorAnalysisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sensorAnalysisId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_data_id", nullable = false)
    private SensorDataEntity sensorData;

    @Column(name = "max_value")
    private BigDecimal maxValue;

    @Column(name = "min_value")
    private BigDecimal minValue;

    @Column(name = "avg_value")
    private BigDecimal avgValue;

    public void update(BigDecimal maxValue, BigDecimal minValue, BigDecimal avgValue) {
        this.maxValue = maxValue;
        this.minValue = minValue;
        this.avgValue = avgValue;
    }
}
