package com.factory.tycoon.prediction.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PredictionRequest {
    private Long factoryId;
    private String type;
    private String level;
    private String message;
    private Boolean selected;
}
