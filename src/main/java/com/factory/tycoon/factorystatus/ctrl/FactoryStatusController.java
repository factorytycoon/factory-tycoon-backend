package com.factory.tycoon.factorystatus.ctrl;

import com.factory.tycoon.factorystatus.domain.dto.FactoryStatusRequest;
import com.factory.tycoon.factorystatus.domain.dto.FactoryStatusResponse;
import com.factory.tycoon.factorystatus.service.FactoryStatusService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/factoryStatus")
@RequiredArgsConstructor
public class FactoryStatusController {

    private final FactoryStatusService factoryStatusService;

    @Operation(summary = "공장 상태 조회", 
            description = "특정 날짜의 공장 상태 및 점수를 조회합니다.")
    @GetMapping
    public ResponseEntity<FactoryStatusResponse> getDailyFactoryRank(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        FactoryStatusResponse result = factoryStatusService.analyzeFactoryStatus(date);
        return ResponseEntity.ok(result);
    }

    
    @Operation(summary = "공장 상태 생성/갱신", 
            description = "특정 날짜의 공장 상태를 계산하여 저장하거나 갱신합니다.")
    @PostMapping
    public ResponseEntity<FactoryStatusResponse> createOrUpdateFactoryStatus(
            @RequestBody FactoryStatusRequest request,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        String factoryId = getFactoryIdFromCurrentUser();  // 현재 사용자의 factoryId 가져오기
        FactoryStatusResponse result = factoryStatusService.calculateAndSaveFactoryStatus(date, factoryId);
        return ResponseEntity.ok(result);
    }
    
    // Security context에서 factoryId 추출 (구현은 상황에 따라 다름)
    private String getFactoryIdFromCurrentUser() {
        // 예: JWT에서 추출 또는 DB에서 현재 사용자의 factoryId 조회
        return "1"; // 임시값 - 실제 로직으로 대체
    }
    @Operation(summary = "공장 상태 삭제", 
            description = "특정 날짜의 공장 상태 데이터를 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<Void> deleteFactoryStatus(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        factoryStatusService.deleteFactoryStatus(date);
        return ResponseEntity.ok().build();
    }
}
