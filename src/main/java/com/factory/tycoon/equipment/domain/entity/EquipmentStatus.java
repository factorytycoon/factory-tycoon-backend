package com.factory.tycoon.equipment.domain.entity;

import java.util.Arrays;

public enum EquipmentStatus {
    normal,
    warning,
    error,
    on,
    off;

    public static EquipmentStatus from(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid equipment status: " + value));
    }
}
