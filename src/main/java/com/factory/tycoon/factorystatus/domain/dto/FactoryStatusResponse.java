package com.factory.tycoon.factorystatus.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class FactoryStatusResponse {
    private String factoryStatusId;
    private String date;
    private int totalScore100; // 100점 환산 점수
    private String rank;          // SS, S, A, B, C
    private List<CategoryScore> details;
    
    // 원본 데이터 필드
    private int safetyAlertCount;       // 안전: 이상 감지 알림 횟수
    private long targetProduction;      // 생산: 목표 생산량
    private long actualProduction;      // 생산: 실제 생산량
    private BigDecimal avgProfit;       // 수익: 평균 수익
    private BigDecimal currentProfit;   // 수익: 현재 수익
    private double defectRate;          // 품질: 불량률 (%)
    private double operationRate;       // 효율: 가동률 (%)
    private boolean maintenanceDone;    // 관리: 설비 점검 여부

    @Data
    @Builder
    public static class CategoryScore {
        private String category; // 항목명 (안전, 생산 등)
        private int score;       // 획득 점수
        private int maxScore;    // 만점 (30)
        private String note;     // 비고 (상세 사유)

    }
}
