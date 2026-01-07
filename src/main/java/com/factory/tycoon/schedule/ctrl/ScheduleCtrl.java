package com.factory.tycoon.schedule.ctrl;

import com.factory.tycoon.schedule.domain.dto.ScheduleRequest;
import com.factory.tycoon.schedule.domain.dto.ScheduleResponse;
import com.factory.tycoon.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Schedule", description = "스케줄 관리 API")
@RestController
@RequestMapping("/api/v1/ft/schedule")
@RequiredArgsConstructor
public class ScheduleCtrl {

    @Operation(summary = "조건별 작업자 조회", description = "workorderId, date, shift로 worker name 리스트 조회")
    @GetMapping("/find-worker-by-date-shift")
    public ResponseEntity<List<String>> findWorkerByDateShift(
            @RequestParam Long workorderId,
            @RequestParam String date,
            @RequestParam String shift) {
        java.time.LocalDate localDate = java.time.LocalDate.parse(date);
        List<String> workers = scheduleService.findWorkerByDateShift(workorderId, localDate, shift);
        return ResponseEntity.ok(workers);
    }

    private final ScheduleService scheduleService;

    @Operation(summary = "스케줄 목록 조회", 
               description = "모든 스케줄 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> getSchedules() {
        return ResponseEntity.ok(scheduleService.getSchedules());
    }

    @Operation(summary = "스케줄 생성", 
               description = "새로운 스케줄을 생성합니다.")
    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(@RequestBody ScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.createSchedule(request));
    }

    @Operation(summary = "스케줄 단건 조회", 
               description = "특정 스케줄의 상세 정보를 조회합니다.")
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> getSchedule(
            @Parameter(description = "스케줄 ID", required = true) @PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.getSchedule(scheduleId));
    }

    @Operation(summary = "스케줄 수정", 
               description = "기존 스케줄의 정보를 수정합니다.")
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> updateSchedule(
            @Parameter(description = "스케줄 ID", required = true) @PathVariable Long scheduleId,
            @RequestBody ScheduleRequest request) {
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleId, request));
    }

    @Operation(summary = "스케줄 삭제", 
               description = "특정 스케줄을 삭제합니다.")
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @Parameter(description = "스케줄 ID", required = true) @PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.ok().build();
    }
}
