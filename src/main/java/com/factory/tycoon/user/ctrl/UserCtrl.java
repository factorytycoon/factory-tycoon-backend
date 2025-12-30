package com.factory.tycoon.user.ctrl;

import com.factory.tycoon.user.domain.dto.UserRequest;
import com.factory.tycoon.user.domain.dto.UserResponse;
import com.factory.tycoon.user.service.UserService;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "회원가입/로그인 API")
@RestController
@RequestMapping("/api/v1/ft/user")
@RequiredArgsConstructor
public class UserCtrl {
    @Operation(summary = "공장별 작업자 목록 조회", description = "특정 factoryId의 worker 목록 조회")
    @GetMapping("/factory/{factoryId}/workers")
    public ResponseEntity<List<UserResponse.WorkerResponse>> getWorkersByFactoryId(@PathVariable Long factoryId) {
        return ResponseEntity.ok(userService.findWorkersByFactoryId(factoryId));
    }

    private final UserService userService;
    private final com.factory.tycoon.auth.TokenService tokenService;
    @Operation(summary = "내 정보 조회", description = "현재 로그인한 사용자 정보 반환")
    @GetMapping("/me")
    public ResponseEntity<UserResponse.AuthResponse> getMyInfo(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replaceFirst("(?i)^Bearer ", "");
        Long userId = tokenService.getUserId(token);
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

    @Operation(summary = "회원가입", description = "owner: 업종에 따라 공장코드를 자동 생성 / worker : 공장코드를 입력해 가입")
    @PostMapping("/signup")
    public ResponseEntity<UserResponse.SignupResponse> signup(@RequestBody UserRequest.SignupRequest request) {
        return ResponseEntity.ok(userService.signup(request));
    }

    @Operation(summary = "로그인", description = "이메일/비밀번호로 로그인하고 accessToken을 발급")
    @PostMapping("/login")
    public ResponseEntity<UserResponse.AuthResponse> login(@RequestBody UserRequest.LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @Operation(summary = "로그아웃", description = "refreshToken이 있으면 Redis에서 폐기")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody(required = false) UserRequest.LogoutRequest request) {
        if (request != null && request.refreshToken() != null && !request.refreshToken().isBlank()) {
            userService.logout(request.refreshToken());
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "유저 조회", description = "역할로 유저 목록 조회 (role=worker 또는 role=owner)")
    @GetMapping("/list")
    public ResponseEntity<List<UserResponse.WorkerResponse>> getUsersByRole(@RequestParam String role) {
        return ResponseEntity.ok(userService.findByRole(role));
    }

}