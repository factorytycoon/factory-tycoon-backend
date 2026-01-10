package com.factory.tycoon.prediction.ctrl;

import com.factory.tycoon.prediction.domain.dto.PredictionRequest;
import com.factory.tycoon.prediction.domain.dto.PredictionResponse;
import com.factory.tycoon.prediction.service.PredictionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Prediction", description = "예측값 관리 API")
@RestController
@RequestMapping("/api/v1/ft/prediction")
@RequiredArgsConstructor
public class PredictionCtrl {

    private final PredictionService predictionService;

    @Operation(summary = "예측값 목록 조회", 
               description = "등록된 모든 예측값 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<PredictionResponse>> getAllPredictions() {
        return ResponseEntity.ok(predictionService.getAllPredictions());
    }

    @Operation(summary = "예측값 등록", 
               description = "새로운 예측값을 등록합니다.")
    @PostMapping
    public ResponseEntity<PredictionResponse> createPrediction(@RequestBody PredictionRequest request) {
        return ResponseEntity.ok(predictionService.createPrediction(request));
    }

    @Operation(summary = "예측값 상세 조회", 
               description = "특정 예측값의 상세 정보를 조회합니다.")
    @GetMapping("/{predictionId}")
    public ResponseEntity<PredictionResponse> getPrediction(
            @Parameter(description = "예측값 ID", required = true) @PathVariable Long predictionId) {
        return ResponseEntity.ok(predictionService.getPrediction(predictionId));
    }

    @Operation(summary = "예측값 수정", 
               description = "예측값의 설명을 수정합니다.")
    @PutMapping("/{predictionId}")
    public ResponseEntity<PredictionResponse> updatePrediction(
            @Parameter(description = "예측값 ID", required = true) @PathVariable Long predictionId,
            @RequestBody PredictionRequest request) {
        return ResponseEntity.ok(predictionService.updatePrediction(predictionId, request));
    }

    @Operation(summary = "예측값 삭제", 
               description = "특정 예측값을 삭제합니다.")
    @DeleteMapping("/{predictionId}")
    public ResponseEntity<Void> deletePrediction(
            @Parameter(description = "예측값 ID", required = true) @PathVariable Long predictionId) {
        predictionService.deletePrediction(predictionId);
        return ResponseEntity.ok().build();
    }
}
