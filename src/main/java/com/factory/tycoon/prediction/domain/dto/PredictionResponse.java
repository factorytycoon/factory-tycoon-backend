package com.factory.tycoon.prediction.domain.dto;

import com.factory.tycoon.prediction.domain.entity.PredictionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PredictionResponse {
    private Long predictionId;
    private Long factoryId;
    private String type;
    private String level;
    private String message;
    private Boolean selected;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PredictionResponse(PredictionEntity prediction) {
        this.predictionId = prediction.getPredictionId();
        this.factoryId = prediction.getFactoryId();
        this.type = prediction.getType();
        this.level = prediction.getLevel().getValue();
        this.message = prediction.getMessage();
        this.selected = prediction.getSelected();
        this.createdAt = prediction.getCreatedAt();
        this.updatedAt = prediction.getUpdatedAt();
    }
}
