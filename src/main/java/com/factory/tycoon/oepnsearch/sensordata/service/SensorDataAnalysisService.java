package com.factory.tycoon.oepnsearch.sensordata.service;

import org.springframework.stereotype.Service;

import com.factory.tycoon.oepnsearch.sensordata.document.SensorDataDocument;

import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SensorDataAnalysisService {

    /**
     * 센서 데이터 리스트를 분석하여 통계 정보를 반환합니다.
     */
    public Map<String, Object> analyze(List<SensorDataDocument> dataList) {
        Map<String, Object> result = new HashMap<>();

        if (dataList == null || dataList.isEmpty()) {
            result.put("message", "데이터가 없습니다.");
            return result;
        }

        // 1. 전체 데이터 기본 통계 (평균, 최대, 최소)
        DoubleSummaryStatistics stats = dataList.stream()
                .mapToDouble(doc -> doc.getValue() != null ? doc.getValue() : 0.0)
                .summaryStatistics();

        result.put("totalCount", stats.getCount());
        result.put("averageValue", stats.getAverage());
        result.put("maxValue", stats.getMax());
        result.put("minValue", stats.getMin());

        // 2. 공장별(factory_id) 평균 값 계산
        Map<String, Double> avgByFactory = dataList.stream()
                .collect(Collectors.groupingBy(SensorDataDocument::getFactoryId,
                        Collectors.averagingDouble(doc -> doc.getValue() != null ? doc.getValue() : 0.0)));
        result.put("averageByFactory", avgByFactory);

        return result;
    }
}