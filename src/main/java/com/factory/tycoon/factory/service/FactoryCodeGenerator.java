package com.factory.tycoon.factory.service;

import com.factory.tycoon.factory.repository.FactoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Map;

//업종에 따라 공장코드 생성
//prefix 매핑
@Component
@RequiredArgsConstructor
public class FactoryCodeGenerator {

    private final FactoryRepository factoryRepository;

    private static final DecimalFormat NUM_FORMAT = new DecimalFormat("00000");

    private static final Map<String, String> INDUSTRY_PREFIX = Map.ofEntries(
            Map.entry("식품 · 음료", "F1"),
            Map.entry("기계 · 설비", "M2"),
            Map.entry("전자 · 반도체", "E1"),
            Map.entry("화학 · 석유화학", "C1"),
            Map.entry("자동차", "A1"),
            Map.entry("철강 · 금속", "M1"),
            Map.entry("섬유 · 의류", "T1"),
            Map.entry("목재 · 가구", "R1"),
            Map.entry("의약품 · 바이오", "P1"),
            Map.entry("기타", "X1")
    );

    
    // 업종 문자열을 prefix로 변환

    public String industryToPrefix(String industry) {
        if (industry == null || industry.isBlank()) {
            throw new IllegalArgumentException("industry가 필요합니다.");
        }

        String key = industry.trim().toLowerCase();

        // 2자리 코드인 경우 그대로 반환 (예: F1, M1 등)
        if (key.matches("^[a-z][0-9]$")) {
            return key.toUpperCase();
        }

        String prefix = INDUSTRY_PREFIX.get(key);
        if (prefix == null) {
            throw new IllegalArgumentException("지원하지 않는 업종입니다. industry=" + industry);
        }
        return prefix;
    }

    //다음 공장코드 생성
    @Transactional(readOnly = true)
    public String nextFactoryCode(String prefix) {
        if (prefix == null || prefix.isBlank()) {
            throw new IllegalArgumentException("prefix가 필요합니다.");
        }

        String p = prefix.trim().toUpperCase();
        String max = factoryRepository.findMaxFactoryCodeByPrefix(p); //현재 가장 큰 공장코드 조회

        int nextNumber = 1;
        if (max != null && !max.isBlank()) {

            String[] parts = max.split("-");
            if (parts.length == 2) {
                try {
                    nextNumber = Integer.parseInt(parts[1]) + 1;
                } catch (NumberFormatException ignore) {
                    nextNumber = 1;
                }
            }
        }

        return p + "-" + NUM_FORMAT.format(nextNumber);
    }
}
