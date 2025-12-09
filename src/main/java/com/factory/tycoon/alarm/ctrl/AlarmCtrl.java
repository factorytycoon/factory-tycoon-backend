package com.factory.tycoon.alarm.ctrl;

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

    @Operation(summary = "알람 목록 조회", description = "등록된 모든 알람 목록을 조회하거나 레벨/상태별로 필터링합니다.")
    @GetMapping
    public ResponseEntity<List<AlarmResponse>> getAllAlarms(
            @Parameter(description = "알람 레벨 (warning/critical)") @RequestParam(required = false) String level,
            @Parameter(description = "해결 상태 (true: 해결됨, false: 미해결)") @RequestParam(required = false) Boolean status) {
        return ResponseEntity.ok(alarmService.getAllAlarms(level, status));
    }

    @Operation(summary = "알람 등록", description = "새로운 알람을 등록합니다.")
    @PostMapping
    public ResponseEntity<AlarmResponse> createAlarm(@RequestBody AlarmRequest request) {
        return ResponseEntity.ok(alarmService.createAlarm(request));
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
}
