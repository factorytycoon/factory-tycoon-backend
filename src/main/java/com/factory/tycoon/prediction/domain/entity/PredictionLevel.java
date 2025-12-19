package com.factory.tycoon.prediction.domain.entity;

import java.util.Arrays;

public enum PredictionLevel {
    warning,
    critical;

    public static PredictionLevel from(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Prediction level cannot be null");
        }
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid prediction level: " + value));
    }
}
