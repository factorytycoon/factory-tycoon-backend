package com.factory.tycoon.factorystatus.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "factory_status")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FactoryStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long factoryStatusId;

    private LocalDate date;
    private String factoryId;

    private Integer totalScore100;

    @Column(name = "`rank`") // SQL 예약어 충돌 방지
    private String rank;

    private Integer safetyAlertCount;
    private Long targetProduction;
    private Long actualProduction;
    private BigDecimal avgProfit;
    private BigDecimal currentProfit;
    private Double defectRate;
    private Double operationRate;
    private boolean maintenanceDone;

    public void update(Integer totalScore100, String rank, Integer safetyAlertCount,
                       Long targetProduction, Long actualProduction, BigDecimal avgProfit,
                       BigDecimal currentProfit, Double defectRate, Double operationRate,
                       boolean maintenanceDone) {
        this.totalScore100 = totalScore100;
        this.rank = rank;
        this.safetyAlertCount = safetyAlertCount;
        this.targetProduction = targetProduction;
        this.actualProduction = actualProduction;
        this.avgProfit = avgProfit;
        this.currentProfit = currentProfit;
        this.defectRate = defectRate;
        this.operationRate = operationRate;
        this.maintenanceDone = maintenanceDone;
    }
}