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
            @GetMapping("/factory/{factoryId}/available-workers")
            public ResponseEntity<List<String>> getAvailableWorkers(
                    @PathVariable Long factoryId,
                    @RequestParam("date") String dateStr) {
                java.time.LocalDate date = java.time.LocalDate.parse(dateStr);
                List<String> names = userService.findAvailableWorkers(factoryId, date)
                        .stream()
                        .map(com.factory.tycoon.user.domain.entity.UserEntity::getName)
                        .toList();
                return ResponseEntity.ok(names);
            }
        @Operation(summary = "특정 날짜에 status=0인 유저 조회", description = "date별로 근무 불가인 유저 목록 조회")
        @GetMapping("/unavailable")
        public ResponseEntity<List<com.factory.tycoon.user.domain.entity.UserEntity>> getUnavailableUsersByDate(@RequestParam("date") String dateStr) {
            java.time.LocalDate date = java.time.LocalDate.parse(dateStr);
            return ResponseEntity.ok(userService.findUnavailableUsersByDate(date));
        }
    private final UserService userService;
    private final com.factory.tycoon.auth.TokenService tokenService;
    
    
    @Operation(summary = "공장별 작업자 목록 조회", description = "특정 factoryId의 worker 목록 조회")
    @GetMapping("/factory/{factoryId}/workers")
    public ResponseEntity<List<UserResponse.WorkerResponse>> getWorkersByFactoryId(@PathVariable Long factoryId) {
        return ResponseEntity.ok(userService.findWorkersByFactoryId(factoryId));
    }

    @Operation(summary = "내 인증 정보 조회", description = "현재 로그인한 사용자 인증 정보 반환")
    @GetMapping("/me")
    public ResponseEntity<UserResponse.AuthResponse> getMyAuth(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replaceFirst("(?i)^Bearer ", "");
        Long userId = tokenService.getUserId(token);
        return ResponseEntity.ok(userService.getUserAuth(userId));
    }
    
    @Operation(summary = "내 정보 조회", description = "현재 로그인한 사용자 정보 반환")
    @GetMapping("/my_info")
    public ResponseEntity<UserResponse.WorkerResponse> getMyInfo(@RequestHeader("Authorization") String authorizationHeader) {
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

    @Operation(summary = "유저 조회", description = "공장별 역할로 유저 목록 조회 (role=worker 또는 role=owner)")
    @GetMapping("/factory/{factoryId}/list")
    public ResponseEntity<List<UserResponse.WorkerResponse>> getUsersByRole(
            @PathVariable Long factoryId,
            @RequestParam String role) {
        return ResponseEntity.ok(userService.findByFactoryAndRole(factoryId, role));
    }

    @Operation(summary = "내 이미지 수정", description = "S3 파일명을 저장하여 사용자 이미지 업데이트")
    @PutMapping("/image_update")
    public ResponseEntity<UserResponse.UpdateImageResponse> updateMyImage(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody UserRequest.UpdateImageRequest request) {
        String token = authorizationHeader.replaceFirst("(?i)^Bearer ", "");
        Long userId = tokenService.getUserId(token);
        return ResponseEntity.ok(userService.updateUserImage(userId, request));
    }

    @Operation(summary = "직원 추가", description = "factoryCode를 기준으로 worker를 등록")
    @PostMapping
    public ResponseEntity<UserResponse.WorkerResponse> createWorker(
            @RequestBody UserRequest.ManageWorkerRequest request) {
        return ResponseEntity.ok(userService.createWorker(request));
    }

    @Operation(summary = "직원 수정", description = "이름/이메일/전화/공장코드를 수정")
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse.WorkerResponse> updateWorker(
            @PathVariable Long userId,
            @RequestBody UserRequest.ManageWorkerRequest request) {
        return ResponseEntity.ok(userService.updateWorker(userId, request));
    }

    @Operation(summary = "직원 삭제", description = "userId로 직원 삭제")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
    
    
    
}