package com.factory.tycoon.oepnsearch.sensordata.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.factory.tycoon.oepnsearch.sensordata.document.SensorDataDocument;
import com.factory.tycoon.oepnsearch.sensordata.service.SensorDataAnalysisService;
import com.factory.tycoon.oepnsearch.sensordata.service.SensorDataSearchService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sensor-data")
public class SensorDataSearchController {

    @Autowired
    private SensorDataSearchService sensorDataSearchService;

    @Autowired
    private SensorDataAnalysisService sensorDataAnalysisService;

    @GetMapping("/daily")
    public ResponseEntity<List<SensorDataDocument>> getDailySensorData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<SensorDataDocument> data = sensorDataSearchService.searchDailySensorData(date);
            return ResponseEntity.ok(data);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/daily/analysis")
    public ResponseEntity<Map<String, Object>> getDailySensorDataAnalysis(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            // 1. 데이터 조회
            List<SensorDataDocument> data = sensorDataSearchService.searchDailySensorData(date);
            // 2. 데이터 분석
            Map<String, Object> analysisResult = sensorDataAnalysisService.analyze(data);
            return ResponseEntity.ok(analysisResult);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
