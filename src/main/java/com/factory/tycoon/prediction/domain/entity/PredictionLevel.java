package com.factory.tycoon.prediction.domain.entity;

public enum PredictionLevel {
    WARNING("warning"),
    CRITICAL("critical");

    private final String value;

    PredictionLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PredictionLevel from(String value) {
        for (PredictionLevel level : PredictionLevel.values()) {
            if (level.value.equalsIgnoreCase(value)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid prediction level: " + value);
    }
}
