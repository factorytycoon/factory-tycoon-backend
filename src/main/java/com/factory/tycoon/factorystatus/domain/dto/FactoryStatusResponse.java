package com.factory.tycoon.factorystatus.domain.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class FactoryStatusResponse {
    private String factoryStatusId;
    private String date;
    private int totalScore100; // 100점 환산 점수
    private String rank;          // SS, S, A, B, C
    private List<CategoryScore> details;
    private FactoryStatusRequest rawData; // 계산에 사용된 원본 데이터

    @Data
    @Builder
    public static class CategoryScore {
        private String category; // 항목명 (안전, 생산 등)
        private int score;       // 획득 점수
        private int maxScore;    // 만점 (30)
        private String note;     // 비고 (상세 사유)

    }
}
