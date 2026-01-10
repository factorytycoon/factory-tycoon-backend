package com.factory.tycoon.prediction.domain.dto;

import com.factory.tycoon.prediction.domain.entity.PredictionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PredictionResponse {
    private Long predictionId;
    private Long userId;
    private String description;
    private LocalDateTime createdAt;

    public PredictionResponse(PredictionEntity prediction) {
        this.predictionId = prediction.getPredictionId();
        this.userId = prediction.getUserId();
        this.description = prediction.getDescription();
        this.createdAt = prediction.getCreatedAt();
    }
}
