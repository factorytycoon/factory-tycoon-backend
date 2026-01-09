package com.factory.tycoon.sensordata.ctrl;

import com.factory.tycoon.sensoranalysis.domain.dto.SensorAnalysisResponse;
import com.factory.tycoon.sensordata.domain.dto.SensorDataRequest;
import com.factory.tycoon.sensordata.domain.dto.SensorDataResponse;
import com.factory.tycoon.sensordata.service.SensorDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "SensorData", description = "센서 데이터 관리 API")
@RestController
@RequestMapping("/api/v1/ft/sensor-data")
@RequiredArgsConstructor
public class SensorDataCtrl {

    private final SensorDataService sensorDataService;

    @Operation(summary = "센서 데이터 목록 조회", description = "등록된 모든 센서 데이터 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<SensorDataResponse>> getAllSensorData() {
        return ResponseEntity.ok(sensorDataService.getAllSensorData());
    }

    @Operation(summary = "센서 데이터 등록", description = "새로운 센서 데이터를 등록합니다.")
    @PostMapping
    public ResponseEntity<SensorDataResponse> createSensorData(@RequestBody SensorDataRequest request) {
        return ResponseEntity.ok(sensorDataService.createSensorData(request));
    }

    @Operation(summary = "센서 데이터 상세 조회", description = "특정 센서 데이터의 상세 정보를 조회합니다.")
    @GetMapping("/{sensorDataId}")
    public ResponseEntity<SensorDataResponse> getSensorData(
            @Parameter(description = "센서 데이터 ID", required = true) @PathVariable Long sensorDataId) {
        return ResponseEntity.ok(sensorDataService.getSensorData(sensorDataId));
    }

    @Operation(summary = "센서 데이터 삭제", description = "특정 센서 데이터를 삭제합니다.")
    @DeleteMapping("/{sensorDataId}")
    public ResponseEntity<Void> deleteSensorData(
            @Parameter(description = "센서 데이터 ID", required = true) @PathVariable Long sensorDataId) {
        sensorDataService.deleteSensorData(sensorDataId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "센서 분석 조회", description = "특정 센서 데이터의 분석 결과를 조회합니다.")
    @GetMapping("/{sensorDataId}/sensor-analysis")
    public ResponseEntity<SensorAnalysisResponse> getSensorAnalysis(
            @Parameter(description = "센서 데이터 ID", required = true) @PathVariable Long sensorDataId) {
        return ResponseEntity.ok(sensorDataService.getSensorAnalysis(sensorDataId));
    }
}
