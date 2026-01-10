package com.factory.tycoon.factorystatus.ctrl;

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
    public ResponseEntity<FactoryStatusResponse> createFactoryStatus(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(factoryStatusService.calculateAndSaveFactoryStatus(date));
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
