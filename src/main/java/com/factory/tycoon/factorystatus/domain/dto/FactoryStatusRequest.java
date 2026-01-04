package com.factory.tycoon.factorystatus.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FactoryStatusRequest {
    private int safetyAlertCount;       // 안전: 이상 감지 알림 횟수
    private long targetProduction;      // 생산: 목표 생산량
    private long actualProduction;      // 생산: 실제 생산량
    private BigDecimal avgProfit;       // 수익: 평균 수익
    private BigDecimal currentProfit;   // 수익: 현재 수익
    private double defectRate;          // 품질: 불량률 (%)
    private double operationRate;       // 효율: 가동률 (%)
    private boolean maintenanceDone;    // 관리: 설비 점검 여부
}