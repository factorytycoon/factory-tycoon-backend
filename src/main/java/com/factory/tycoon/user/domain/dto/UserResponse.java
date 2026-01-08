package com.factory.tycoon.user.domain.dto;

public class UserResponse {

    public record SignupResponse(
            Long userId,
            Long factoryId,
            String role,
            String factoryCode // owner 가입 시 발급된 코드
    ) {}

    public record AuthResponse(
            Long userId,
            Long factoryId,
            String role,
            String accessToken
    ) {}

    public record WorkerResponse(
            Long userId,
            String name,
            String email,
            String phone,
            String role,
            Boolean status, // 0: 배치 미완료, 1: 배치 완료
            Long factoryId,
            String factoryCode,
            String image
    ) {}

    public record UpdateImageResponse(
            Long userId,
            String image
    ) {}
}
