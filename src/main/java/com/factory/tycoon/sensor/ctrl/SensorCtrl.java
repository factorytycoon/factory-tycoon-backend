package com.factory.tycoon.sensor.ctrl;

import com.factory.tycoon.sensor.domain.dto.SensorRequest;
import com.factory.tycoon.sensor.domain.dto.SensorResponse;
import com.factory.tycoon.sensor.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Sensor", description = "센서 관리 API")
@RestController
@RequestMapping("/api/v1/ft/sensor")
@RequiredArgsConstructor
public class SensorCtrl {

    private final SensorService sensorService;

    @Operation(summary = "센서 목록 조회", description = "등록된 모든 센서 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<SensorResponse>> getSensors() {
        return ResponseEntity.ok(sensorService.getSensors());
    }

    @Operation(summary = "센서 등록", description = "새로운 센서를 등록합니다.")
    @PostMapping
    public ResponseEntity<SensorResponse> createSensor(@RequestBody SensorRequest request) {
        return ResponseEntity.ok(sensorService.createSensor(request));
    }

    @Operation(summary = "센서 상세 조회", description = "특정 센서의 상세 정보를 조회합니다.")
    @GetMapping("/{sensorId}")
    public ResponseEntity<SensorResponse> getSensor(
            @Parameter(description = "센서 ID", required = true) @PathVariable Long sensorId) {
        return ResponseEntity.ok(sensorService.getSensor(sensorId));
    }

    @Operation(summary = "센서 정보 수정", description = "기존 센서의 정보를 수정합니다.")
    @PutMapping("/{sensorId}")
    public ResponseEntity<SensorResponse> updateSensor(
            @Parameter(description = "센서 ID", required = true) @PathVariable Long sensorId,
            @RequestBody SensorRequest request) {
        return ResponseEntity.ok(sensorService.updateSensor(sensorId, request));
    }

    @Operation(summary = "센서 삭제", description = "특정 센서를 삭제합니다.")
    @DeleteMapping("/{sensorId}")
    public ResponseEntity<Void> deleteSensor(
            @Parameter(description = "센서 ID", required = true) @PathVariable Long sensorId) {
        sensorService.deleteSensor(sensorId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "센서 알람 조회", description = "특정 센서에 대한 알람 정보를 조회합니다.")
    @GetMapping("/{sensorId}/alarms")
    public ResponseEntity<List<Object>> getAlarms(
            @Parameter(description = "센서 ID", required = true) @PathVariable Long sensorId) {
        return ResponseEntity.ok(sensorService.getAlarms(sensorId));
    }

    @Operation(summary = "센서 데이터 조회", description = "센서 데이터 전체 또는 필터(날짜, 센서타입)로 조회합니다.")
    @GetMapping("/{sensorId}/sensor-data")
    public ResponseEntity<List<Object>> getSensorData(
            @Parameter(description = "센서 ID", required = true) @PathVariable Long sensorId,
            @Parameter(description = "조회 날짜(yyyy-MM-dd)") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(description = "센서 타입") @RequestParam(required = false) String sensorType) {
        return ResponseEntity.ok(sensorService.getSensorData(sensorId, date, sensorType));
    }
}
