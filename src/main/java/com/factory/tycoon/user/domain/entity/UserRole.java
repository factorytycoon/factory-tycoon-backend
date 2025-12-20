package com.factory.tycoon.user.domain.entity;

public enum UserRole {
    OWNER,
    WORKER;

    // 문자열로부터 UserRole 변환
    public static UserRole from(String raw) {
        if (raw == null) throw new IllegalArgumentException("role이 필요합니다.");
        String v = raw.trim().toLowerCase();
        return switch (v) {
            case "owner" -> OWNER;
            case "worker" -> WORKER;
            default -> throw new IllegalArgumentException("role은 owner 또는 worker 여야 합니다.");
        };
    }
    // API 응답용 문자열 반환
    public String toApiValue() {
        return this == OWNER ? "owner" : "worker";
    }
}