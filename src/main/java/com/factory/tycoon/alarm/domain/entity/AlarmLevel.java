package com.factory.tycoon.alarm.domain.entity;

import java.util.Arrays;

public enum AlarmLevel {
    warning,
    critical;

    public static AlarmLevel from(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Alarm level cannot be null");
        }
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid alarm level: " + value));
    }
}
