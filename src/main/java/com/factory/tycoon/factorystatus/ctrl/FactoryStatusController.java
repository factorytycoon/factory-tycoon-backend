package com.factory.tycoon.factorystatus.ctrl;

import com.factory.tycoon.factorystatus.domain.dto.FactoryStatusResponse;
import com.factory.tycoon.factorystatus.service.FactoryStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/factoryStatus")
@RequiredArgsConstructor
public class FactoryStatusController {

    private final FactoryStatusService factoryStatusService;

    @GetMapping("/{id}}")
    public ResponseEntity<FactoryStatusResponse> getDailyFactoryRank(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        FactoryStatusResponse result = factoryStatusService.analyzeFactoryStatus(date);
        return ResponseEntity.ok(result);
    }
}
