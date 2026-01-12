package com.factory.tycoon.factorystatus.service;

import com.factory.tycoon.alarm.domain.entity.AlarmEntity;
import com.factory.tycoon.alarm.repository.AlarmRepository;
import com.factory.tycoon.factorystatus.domain.dto.FactoryStatusRequest;
import com.factory.tycoon.factorystatus.domain.dto.FactoryStatusResponse;
import com.factory.tycoon.factorystatus.domain.dto.FactoryStatusResponse.CategoryScore;
import com.factory.tycoon.factorystatus.domain.entity.FactoryStatusEntity;
import com.factory.tycoon.factorystatus.repository.FactoryStatusRepository;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactoryStatusService {

    private final AlarmRepository alarmRepository;
    private final WorkOrderRepository workOrderRepository;
    private final FactoryStatusRepository factoryStatusRepository;

    private static final int MAX_SCORE_PER_CATEGORY = 30;
    private static final int TOTAL_MAX_SCORE = 180; // 30점 * 6개 항목

    // 날짜별 상태 조회 (DB 우선 조회, 없으면 실시간 계산)
    public FactoryStatusResponse analyzeFactoryStatus(LocalDate date) {
        // 1. DB에 저장된 데이터가 있는지 확인
        Optional<FactoryStatusEntity> entityOpt = factoryStatusRepository.findByDate(date);
        if (entityOpt.isPresent()) {
            return mapEntityToResponse(entityOpt.get());
        }

        // 2. 없으면 원천 데이터에서 계산 (실시간 집계)
        return calculateMetrics(date);
    }

    private FactoryStatusResponse calculateMetrics(LocalDate date) {
        // 1. 원천 데이터 조회
        FactoryStatusRequest status = calculateDailyMetricsFromSource(date);
        if (status == null) {
            throw new IllegalStateException("FactoryStatusRequest 생성 실패");
        }
        
        // 2. 점수 및 상세 내역 계산
        List<CategoryScore> details = calculateCategoryScores(status);
        
        // 3. 총점 계산
        int totalRawScore = details.stream().mapToInt(CategoryScore::getScore).sum();
        int finalScore100 = (int) ((double) totalRawScore / TOTAL_MAX_SCORE * 100);
        String rank = determineRank(finalScore100);

        return buildResponse(date, status, finalScore100, rank, details);
    }

    private FactoryStatusResponse buildResponse(LocalDate date, FactoryStatusRequest status, 
                                                int totalScore100, String rank, List<CategoryScore> details) {
        return FactoryStatusResponse.builder()
                .date(date.toString())
                .totalScore100(totalScore100)
                .rank(rank)
                .details(details)
                .safetyAlertCount(status.getSafetyAlertCount())      
                .targetProduction(status.getTargetProduction())
                .actualProduction(status.getActualProduction())
                .avgProfit(status.getAvgProfit())
                .currentProfit(status.getCurrentProfit())
                .defectRate(status.getDefectRate())
                .operationRate(status.getOperationRate())
                .maintenanceDone(status.isMaintenanceDone()) 
                .build();
    }

    // 해당 날짜의 데이터를 집계하여 DB에 저장 또는 갱신
    @org.springframework.transaction.annotation.Transactional
    public FactoryStatusResponse calculateAndSaveFactoryStatus(LocalDate date) {
        // 1. 점수 계산
        FactoryStatusResponse calculated = calculateMetrics(date);

        // 2. DB 저장 (이미 존재하면 업데이트)
        FactoryStatusEntity entity = factoryStatusRepository.findByDate(date)
                .orElse(FactoryStatusEntity.builder()
                        .date(date)
                        .factoryId("1") // 기본 공장 ID
                        .build());

        entity.update(
                calculated.getTotalScore100(),
                calculated.getRank(),
                calculated.getSafetyAlertCount(),
                calculated.getTargetProduction(),
                calculated.getActualProduction(),
                calculated.getAvgProfit(),
                calculated.getCurrentProfit(),
                calculated.getDefectRate(),
                calculated.getOperationRate(),
                calculated.isMaintenanceDone()
        );

        factoryStatusRepository.save(entity);
        return calculated;
    }

    // [DELETE] 데이터 삭제
    @org.springframework.transaction.annotation.Transactional
    public void deleteFactoryStatus(LocalDate date) {
        factoryStatusRepository.deleteByDate(date);
    }

    // Entity -> Response 변환
    private FactoryStatusResponse mapEntityToResponse(FactoryStatusEntity entity) {
        
        // 1. Entity 데이터를 기반으로 Request 객체(지표 모음) 복원
        FactoryStatusRequest status = FactoryStatusRequest.builder()
                .safetyAlertCount(entity.getSafetyAlertCount())
                .targetProduction(entity.getTargetProduction())
                .actualProduction(entity.getActualProduction())
                .avgProfit(entity.getAvgProfit())
                .currentProfit(entity.getCurrentProfit())
                .defectRate(entity.getDefectRate())
                .operationRate(entity.getOperationRate())
                .maintenanceDone(entity.isMaintenanceDone())
                .build();

        // 2. 저장된 지표를 바탕으로 상세 내역(CategoryScore)만 재구성
        // (DB에 저장된 총점과 랭크를 그대로 사용하기 위해 계산 로직은 details 생성에만 사용)
        List<CategoryScore> details = calculateCategoryScores(status);

        // 3. DB에 저장된 총점과 랭크를 사용하여 응답 생성
        return buildResponse(entity.getDate(), status, entity.getTotalScore100(), entity.getRank(), details);
    }

    // 점수 계산 로직 분리 (재사용을 위해)
    private List<CategoryScore> calculateCategoryScores(FactoryStatusRequest status) {
        List<CategoryScore> details = new ArrayList<>();

        // [안전] 0회면 만점, 10회당 1점 감점
        int safetyScore = Math.max(0, MAX_SCORE_PER_CATEGORY - (status.getSafetyAlertCount() / 10));
        details.add(createCategoryScore("안전", safetyScore, "이상 감지: " + status.getSafetyAlertCount() + "회"));

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

        // [관리] 설비 점검 수행 여부 (수행 시 만점, 미수행 시 15점)
        int managementScore = status.isMaintenanceDone() ? MAX_SCORE_PER_CATEGORY : 15;
        details.add(createCategoryScore("관리", managementScore, status.isMaintenanceDone() ? "점검 완료" : "점검 미완료"));

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

        // [품질] 불량률 1%당 2점 감점
        int qualityPenalty = (int) (status.getDefectRate() * 2);
        int qualityScore = Math.max(0, MAX_SCORE_PER_CATEGORY - qualityPenalty);
        details.add(createCategoryScore("품질", qualityScore, String.format("불량률: %.1f%%", status.getDefectRate())));

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

        return details;
    }

    private CategoryScore createCategoryScore(String category, int score, String note) {
        return CategoryScore.builder()
                .category(category)
                .score(score)
                .maxScore(MAX_SCORE_PER_CATEGORY)
                .note(note)
                .build();
    }

    private String determineRank(int score) {
        if (score >= 100) return "SS";
        if (score >= 95) return "S";
        if (score >= 90) return "A";
        if (score >= 85) return "B";
        if (score >= 80) return "C";
        return "C";
    }

    // 원천 데이터(Alarm, WorkOrder)에서 지표 집계
    private FactoryStatusRequest calculateDailyMetricsFromSource(LocalDate date) {
        // 1. 안전: 알람 테이블(Alarm)에서 카운트
        // 요청받은 날짜(date)를 기준으로 어제(date-1)의 데이터를 조회
        LocalDate targetDate = date.minusDays(1);
        LocalDateTime startOfDay = targetDate.atStartOfDay();
        LocalDateTime nextDayStart = targetDate.plusDays(1).atStartOfDay();
        List<AlarmEntity> alarms =
            alarmRepository.findAllBySensorDtBetween(startOfDay, nextDayStart);

        int safetyAlertCount = alarms.size();

        // 2. 오더 데이터 가져오기 (WorkOrder) - 오늘 날짜 데이터만 조회 (최적화)
        List<WorkOrderEntity> todayOrders = workOrderRepository.findByCreatedAtBetween(startOfDay, nextDayStart);
        if (todayOrders == null) todayOrders = List.of();

        // 3. 생산: 목표 생산량 (오늘 오더의 목표량 합계)
        long targetProduction = todayOrders.stream()
                .mapToLong(WorkOrderEntity::getTargetAmount)
                .sum();

        // 실제 생산량 (완료된 오더의 목표량 합계)
        long actualProduction = todayOrders.stream()
                .filter(o -> Boolean.TRUE.equals(o.getStatus()))
                .mapToLong(WorkOrderEntity::getTargetAmount)
                .sum();

        // 4. 수익
        // 현재 수익 (오늘 오더의 가격 합계)
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

        // 5. 평균 수익 (전체 데이터 조회 필요 - 성능을 위해 별도 쿼리 권장되나 기존 로직 유지 시 findAll 사용)
        List<WorkOrderEntity> allOrders = workOrderRepository.findAll();
        // 평균 수익 (전체 기간의 일평균 수익)
        BigDecimal totalProfitAllTime = allOrders.stream()
            .map(o -> {
                try {
                    return o.getPrice() != null ? new BigDecimal(o.getPrice()) : BigDecimal.ZERO;
                } catch (Exception e) {
                    return BigDecimal.ZERO;
                }
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<LocalDate, List<WorkOrderEntity>> ordersByDate = allOrders.stream()
                .filter(o -> o.getCreatedAt() != null)
                .collect(Collectors.groupingBy(o -> o.getCreatedAt().toLocalDate()));

        BigDecimal avgProfit = BigDecimal.ZERO;
        if (!ordersByDate.isEmpty() && totalProfitAllTime.compareTo(BigDecimal.ZERO) > 0) {
            avgProfit = totalProfitAllTime.divide(BigDecimal.valueOf(ordersByDate.size()), 0, RoundingMode.HALF_UP);
        }

        // 6. 품질(불량률) 및 효율(가동률) 동적 계산
        // 불량률: (목표량 - 실제생산량) / 목표량 * 100
        double defectRate = targetProduction > 0 
                ? (double) (targetProduction - actualProduction) / targetProduction * 100 
                : 0.0;
        
        // 가동률: 기본 100%에서 알람 1회당 0.5% 차감 (최소 0%)
        double operationRate = Math.max(0.0, 100.0 - (safetyAlertCount * 0.5));

        return FactoryStatusRequest.builder()
                .safetyAlertCount(safetyAlertCount)
                .targetProduction(targetProduction)
                .actualProduction(actualProduction)
                .avgProfit(avgProfit)
                .currentProfit(currentProfit)
                .defectRate(Math.round(defectRate * 10.0) / 10.0) // 소수점 첫째자리 반올림
                .operationRate(Math.round(operationRate * 10.0) / 10.0)
                .maintenanceDone(safetyAlertCount == 0) // 알람이 없으면 점검 완료로 간주
                .build();
    }
}