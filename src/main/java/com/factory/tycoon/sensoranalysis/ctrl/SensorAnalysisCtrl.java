package com.factory.tycoon.sensoranalysis.ctrl;

import com.factory.tycoon.sensoranalysis.domain.dto.SensorAnalysisRequest;
import com.factory.tycoon.sensoranalysis.domain.dto.SensorAnalysisResponse;
import com.factory.tycoon.sensoranalysis.service.SensorAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "SensorAnalysis", description = "센서 분석 관리 API")
@RestController
@RequestMapping("/api/v1/ft/sensor-analysis")
@RequiredArgsConstructor
public class SensorAnalysisCtrl {

    private final SensorAnalysisService sensorAnalysisService;

    @Operation(summary = "센서 분석 목록 조회", description = "등록된 모든 센서 분석 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<SensorAnalysisResponse>> getAllSensorAnalysis() {
        return ResponseEntity.ok(sensorAnalysisService.getAllSensorAnalysis());
    }

    @Operation(summary = "센서 분석 등록", description = "새로운 센서 분석 데이터를 등록합니다.")
    @PostMapping
    public ResponseEntity<SensorAnalysisResponse> createSensorAnalysis(@RequestBody SensorAnalysisRequest request) {
        return ResponseEntity.ok(sensorAnalysisService.createSensorAnalysis(request));
    }

    @Operation(summary = "센서 분석 상세 조회", description = "특정 센서 분석의 상세 정보를 조회합니다.")
    @GetMapping("/{sensorAnalysisId}")
    public ResponseEntity<SensorAnalysisResponse> getSensorAnalysis(
            @Parameter(description = "센서 분석 ID", required = true) @PathVariable Long sensorAnalysisId) {
        return ResponseEntity.ok(sensorAnalysisService.getSensorAnalysis(sensorAnalysisId));
    }

    @Operation(summary = "센서 분석 삭제", description = "특정 센서 분석을 삭제합니다.")
    @DeleteMapping("/{sensorAnalysisId}")
    public ResponseEntity<Void> deleteSensorAnalysis(
            @Parameter(description = "센서 분석 ID", required = true) @PathVariable Long sensorAnalysisId) {
        sensorAnalysisService.deleteSensorAnalysis(sensorAnalysisId);
        return ResponseEntity.ok().build();
    }
}
