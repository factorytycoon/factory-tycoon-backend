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
            String location,
            String industry,    // owner만 사용(저장 X)
            String factoryCode,  // worker만 사용
            String factoryName, // owner만 사용
            Boolean status // 0: 배치 미완료, 1: 배치 완료
    ) {}

    public record LoginRequest(
            String email,
            String password
    ) {}

    public record LogoutRequest(
            String refreshToken // 지금은 안 쓰면 null 가능
    ) {}

    public record UpdateImageRequest(
            String image // S3 file name (nullable)
    ) {}
    
        public record ManageWorkerRequest(
                String name,
                String email,
                String phone,
                String factoryCode,
                String role,
                java.time.LocalDate dob,
                String password,
                Boolean status // 0: 배치 미완료, 1: 배치 완료
        ) {}
}
