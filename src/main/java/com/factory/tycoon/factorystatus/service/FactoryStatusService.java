package com.factory.tycoon.factorystatus.service;

import com.factory.tycoon.alarm.domain.entity.AlarmEntity;
import com.factory.tycoon.alarm.repository.AlarmRepository;
import com.factory.tycoon.factorystatus.domain.dto.FactoryStatusRequest;
import com.factory.tycoon.factorystatus.domain.dto.FactoryStatusResponse;
import com.factory.tycoon.factorystatus.domain.dto.FactoryStatusResponse.CategoryScore;
import com.factory.tycoon.workorder.domain.entity.WorkOrderEntity;
import com.factory.tycoon.workorder.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FactoryStatusService {

    private final AlarmRepository alarmRepository;
    private final WorkOrderRepository workOrderRepository;

    private static final int MAX_SCORE_PER_CATEGORY = 30;
    private static final int TOTAL_MAX_SCORE = 180; // 30점 * 6개 항목

    public FactoryStatusResponse analyzeFactoryStatus(LocalDate date) {
        // 1. RDB 데이터 가져오기 (현재는 더미 데이터 사용)
        FactoryStatusRequest status = getDailyFactoryStatusFromRDB(date);
        if (status == null) {
            throw new IllegalStateException("FactoryStatusRequest 생성 실패");
        }
        List<CategoryScore> details = new ArrayList<>();
        int totalRawScore = 0;

        // 2. 각 항목별 점수 계산

        // [안전] 0회면 만점, 1회당 2점 감점
        int safetyScore = Math.max(0, MAX_SCORE_PER_CATEGORY - (status.getSafetyAlertCount() * 2));
        details.add(createCategoryScore("안전", safetyScore, "이상 감지: " + status.getSafetyAlertCount() + "회"));
        totalRawScore += safetyScore;

        // [생산] 목표 대비 생산량. 100% 이상 만점, 5% 미만마다 2점 감점
        double productionRate = 0;
        if (status.getTargetProduction() > 0) {
            productionRate = (double) status.getActualProduction() / status.getTargetProduction() * 100;
        }
        int productionPenalty = 0;
        if (productionRate < 100) {
            int steps = (int) ((100 - productionRate) / 5);
            if ((100 - productionRate) % 5 > 0) steps++; // 5% 단위 올림 처리
            productionPenalty = steps * 2;
        }
        int productionScore = Math.max(0, MAX_SCORE_PER_CATEGORY - productionPenalty);
        details.add(createCategoryScore("생산", productionScore, String.format("달성률: %.1f%%", productionRate)));
        totalRawScore += productionScore;

        // [관리] 설비 점검 수행 여부 (수행 시 만점, 미수행 시 15점)
        int managementScore = status.isMaintenanceDone() ? MAX_SCORE_PER_CATEGORY : 15;
        details.add(createCategoryScore("관리", managementScore, status.isMaintenanceDone() ? "점검 완료" : "점검 미완료"));
        totalRawScore += managementScore;

        // [수익] 평균(20점) 기준. 5% 상승마다 +2, 5% 하락마다 -2
        BigDecimal avgProfit = status.getAvgProfit();
        BigDecimal currentProfit = status.getCurrentProfit();
        int profitScore = 20;

        if (avgProfit != null && avgProfit.compareTo(BigDecimal.ZERO) > 0 && currentProfit != null) {
            double profitRateChange = currentProfit.subtract(avgProfit)
                    .divide(avgProfit, 4, RoundingMode.HALF_UP).doubleValue() * 100;

            int steps = (int) (Math.abs(profitRateChange) / 5);
            if (profitRateChange >= 0) {
                profitScore += (steps * 2);
            } else {
                profitScore -= (steps * 2);
            }
        }
        profitScore = Math.min(MAX_SCORE_PER_CATEGORY, Math.max(0, profitScore));
        details.add(createCategoryScore("수익", profitScore, "평균 대비 변동 반영"));
        totalRawScore += profitScore;

        // [품질] 불량률 1%당 2점 감점
        int qualityPenalty = (int) (status.getDefectRate() * 2);
        int qualityScore = Math.max(0, MAX_SCORE_PER_CATEGORY - qualityPenalty);
        details.add(createCategoryScore("품질", qualityScore, String.format("불량률: %.1f%%", status.getDefectRate())));
        totalRawScore += qualityScore;

        // [효율] 가동률 100% 기준, 5% 미가동마다 2점 감점
        double operationRate = status.getOperationRate();
        int efficiencyPenalty = 0;
        if (operationRate < 100) {
            int steps = (int) ((100 - operationRate) / 5);
            if ((100 - operationRate) % 5 > 0) steps++;
            efficiencyPenalty = steps * 2;
        }
        int efficiencyScore = Math.max(0, MAX_SCORE_PER_CATEGORY - efficiencyPenalty);
        details.add(createCategoryScore("효율", efficiencyScore, String.format("가동률: %.1f%%", operationRate)));
        totalRawScore += efficiencyScore;

        // 3. 최종 점수 및 랭크 산정
        double finalScore100 = (double) totalRawScore / TOTAL_MAX_SCORE * 100;
        String rank = determineRank(finalScore100);

        return FactoryStatusResponse.builder()
                .date(date.toString())
                .totalScore100(finalScore100)
                .rank(rank)
                .details(details)
                .rawData(status)
                .build();
    }

    private CategoryScore createCategoryScore(String category, int score, String note) {
        return CategoryScore.builder()
                .category(category)
                .score(score)
                .maxScore(MAX_SCORE_PER_CATEGORY)
                .note(note)
                .build();
    }

    private String determineRank(double score) {
        if (score >= 100) return "SS";
        if (score >= 95) return "S";
        if (score >= 90) return "A";
        if (score >= 85) return "B";
        if (score >= 80) return "C";
        return "C";
    }

    private FactoryStatusRequest getDailyFactoryStatusFromRDB(LocalDate date) {
        // 1. 안전: 알람 테이블(Alarm)에서 카운트
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        List<AlarmEntity> alarms =
            alarmRepository.findAllByCreatedAtBetween(startOfDay, endOfDay);

        if (alarms == null) {
            alarms = List.of();
        }
        int safetyAlertCount = alarms.size();

        // 2. 오더 데이터 가져오기 (WorkOrder)
        List<WorkOrderEntity> allOrders = workOrderRepository.findAll();
        if (allOrders == null) {
            allOrders = List.of();
        }
        List<WorkOrderEntity> todayOrders = allOrders.stream()
                .filter(o -> o.getCreatedAt() != null && o.getCreatedAt().toLocalDate().equals(date))
                .toList();

        // 3. 생산: 목표 생산량 (오늘 오더의 목표량 합계)
        long targetProduction = todayOrders.stream()
                .mapToLong(WorkOrderEntity::getTargetAmount)
                .sum();

        // 실제 생산량 (완료된 오더의 목표량 합계)
        long actualProduction = todayOrders.stream()
                .filter(o -> Boolean.TRUE.equals(o.getStatus()))
                .mapToLong(WorkOrderEntity::getTargetAmount)
                .sum();

        // 4. 수익: 현재 수익 (오늘 오더의 가격 합계)
        BigDecimal currentProfit = todayOrders.stream()
            .map(o -> {
                try {
                    return o.getPrice() != null
                        ? new BigDecimal(o.getPrice())
                        : BigDecimal.ZERO;
                } catch (Exception e) {
                    return BigDecimal.ZERO;
                }
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
            
        return FactoryStatusRequest.builder()
                .safetyAlertCount(safetyAlertCount)
                .targetProduction(targetProduction)
                .actualProduction(actualProduction)
                .avgProfit(BigDecimal.valueOf(10000000)) // 평균 수익 하드코딩
                .currentProfit(currentProfit)
                .defectRate(0.5) // 불량률 하드코딩
                .operationRate(98.5) // 가동률 하드코딩
                .maintenanceDone(true) // 점검 여부 하드코딩
                .build();
    }
}