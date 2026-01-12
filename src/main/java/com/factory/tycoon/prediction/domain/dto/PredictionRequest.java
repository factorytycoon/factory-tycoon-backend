package com.factory.tycoon.prediction.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PredictionRequest {
    private Long userId;
    private String description;
}
