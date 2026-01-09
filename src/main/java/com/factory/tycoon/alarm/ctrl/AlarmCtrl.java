package com.factory.tycoon.alarm.ctrl;

import com.factory.tycoon.alarm.domain.dto.AlarmOsRequest;
import com.factory.tycoon.alarm.domain.dto.AlarmRequest;
import com.factory.tycoon.alarm.domain.dto.AlarmResponse;
import com.factory.tycoon.alarm.service.AlarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Alarm", description = "알람 관리 API")
@RestController
@RequestMapping("/api/v1/ft/alarm")
@RequiredArgsConstructor
public class AlarmCtrl {

    private final AlarmService alarmService;

    @Operation(summary = "알람 목록 조회", description = "등록된 모든 알람 목록을 조회하거나 공장/레벨/상태별로 필터링합니다.")
    @GetMapping
    public ResponseEntity<List<AlarmResponse>> getAllAlarms(
            @Parameter(description = "공장 ID") @RequestParam(required = false) Long factoryId,
            @Parameter(description = "알람 레벨 (yellow/orange/red)") @RequestParam(required = false) String level,
            @Parameter(description = "해결 상태 (OPEN: 해결됨, CLOSE: 미해결)") @RequestParam(required = false) String status) {
        return ResponseEntity.ok(alarmService.getAllAlarms(factoryId, level, status));
    }

    @Operation(summary = "알람 등록", description = "새로운 알람을 등록합니다.")
    @PostMapping
    public ResponseEntity<AlarmResponse> createAlarm(@RequestBody AlarmRequest request) {
        return ResponseEntity.ok(alarmService.createAlarm(request));
    }

    @Operation(summary = "알람 등록", description = "opensearch 에서 온 알람을 등록합니다.")
    @PostMapping("/os")
    public ResponseEntity<AlarmResponse> createOsAlarm(@RequestBody AlarmOsRequest request) {
        return ResponseEntity.ok(alarmService.createOsAlarm(request));
    }

    @Operation(summary = "알람 상세 조회", description = "특정 알람의 상세 정보를 조회합니다.")
    @GetMapping("/{alarmId}")
    public ResponseEntity<AlarmResponse> getAlarm(
            @Parameter(description = "알람 ID", required = true) @PathVariable Long alarmId) {
        return ResponseEntity.ok(alarmService.getAlarm(alarmId));
    }

    @Operation(summary = "알람 삭제", description = "특정 알람을 삭제합니다.")
    @DeleteMapping("/{alarmId}")
    public ResponseEntity<Void> deleteAlarm(
            @Parameter(description = "알람 ID", required = true) @PathVariable Long alarmId) {
        alarmService.deleteAlarm(alarmId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "알람 해결", description = "알람을 해결 상태로 변경하고 해당 설비의 상태를 normal로 변경합니다.")
    @PutMapping("/{alarmId}/resolve")
    public ResponseEntity<AlarmResponse> resolveAlarm(
            @Parameter(description = "알람 ID", required = true) @PathVariable Long alarmId) {
        return ResponseEntity.ok(alarmService.resolveAlarm(alarmId));
    }
}
