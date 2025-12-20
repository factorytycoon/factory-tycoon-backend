package com.factory.tycoon.user.domain.dto;

import java.time.LocalDate;

public class UserRequest {

    public record SignupRequest(
            String name,
            LocalDate dob,
            String phone,
            String email,
            String password,
            String role,        // owner / worker
            String industry,    // owner만 사용(저장 X)
            String factoryCode,  // worker만 사용
            String factoryName // owner만 사용
    ) {}

    public record LoginRequest(
            String email,
            String password
    ) {}

    public record LogoutRequest(
            String refreshToken // 지금은 안 쓰면 null 가능
    ) {}
}
