package com.factory.tycoon.user.service;

import com.factory.tycoon.auth.PasswordService;
import com.factory.tycoon.auth.TokenService;
import com.factory.tycoon.factory.domain.entity.FactoryEntity;
import com.factory.tycoon.factory.repository.FactoryRepository;
import com.factory.tycoon.factory.service.FactoryCodeGenerator;
import com.factory.tycoon.user.domain.dto.UserRequest;
import com.factory.tycoon.user.domain.dto.UserResponse;
import com.factory.tycoon.user.domain.entity.UserEntity;
import com.factory.tycoon.user.domain.entity.UserRole;
import com.factory.tycoon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



 //owner: 업종으로 공장코드 자동 발급 -> factory_code 저장 -> user.factory_id 저장
 //worker: factoryCode 입력 -> factory 조회 -> user.factory_id 저장


@Service
@RequiredArgsConstructor
public class UserService {
    // userId로 사용자 정보 반환
    @Transactional(readOnly = true)
    public com.factory.tycoon.user.domain.dto.UserResponse.AuthResponse getUserAuth(Long userId) {
    UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    return new com.factory.tycoon.user.domain.dto.UserResponse.AuthResponse(
        user.getUserId(),
        user.getFactory().getFactoryId(),
        user.getRole().toApiValue(),
        null // accessToken은 반환하지 않음
    );
    }
    
    @Transactional(readOnly = true)
    public com.factory.tycoon.user.domain.dto.UserResponse.WorkerResponse getUserInfo(Long userId) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return new com.factory.tycoon.user.domain.dto.UserResponse.WorkerResponse(
            user.getUserId(),
            user.getName(),
            user.getEmail(),
            user.getPhone(),
            user.getFactory().getFactoryId(),
            user.getFactory().getFactoryCode(),
            user.getImage()
        );
    }

    // 특정 factoryId에 속한 worker 목록 반환
    @Transactional(readOnly = true)
    public java.util.List<UserResponse.WorkerResponse> findWorkersByFactoryId(Long factoryId) {
    com.factory.tycoon.user.domain.entity.UserRole workerRole = com.factory.tycoon.user.domain.entity.UserRole.WORKER;
    return userRepository.findByFactory_FactoryIdAndRole(factoryId, workerRole).stream()
        .map(user -> new UserResponse.WorkerResponse(
            user.getUserId(),
            user.getName(),
            user.getEmail(),
            user.getPhone(),
            user.getFactory().getFactoryId(),
            user.getFactory().getFactoryCode(),
            user.getImage()
        ))
        .toList();
    }

    private final UserRepository userRepository;
    private final FactoryRepository factoryRepository;
    private final FactoryCodeGenerator factoryCodeGenerator;
    private final PasswordService passwordService;
    private final TokenService tokenService;

    //회원가입
    @Transactional
    public UserResponse.SignupResponse signup(UserRequest.SignupRequest req) {

        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        UserRole role = UserRole.from(req.role());// role 파싱 

        FactoryEntity factory = resolveFactory(role, req); //owner는 공장생성, worker는 공장조회

        String hashed = passwordService.hash(req.password());

        UserEntity user = UserEntity.builder() //user 저장
                .factory(factory)
                .name(req.name())
                .dob(req.dob())
                .phone(req.phone())
                .email(req.email())
                .password(hashed)
                .role(role)
                .build();

        UserEntity saved = userRepository.save(user);

        return new UserResponse.SignupResponse( //가입응답
                saved.getUserId(),
                factory.getFactoryId(),
                saved.getRole().toApiValue(),
                factory.getFactoryCode()
        );
    }

    //로그인
    @Transactional(readOnly = true)
    public UserResponse.AuthResponse login(UserRequest.LoginRequest req) {
        UserEntity user = userRepository.findByEmail(req.email())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        boolean ok = passwordService.matches(req.password(), user.getPassword());
        if (!ok) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        // accessToken 발급
        String accessToken = tokenService.createAccessToken(user.getUserId(), user.getRole().toApiValue());

        return new UserResponse.AuthResponse(
                user.getUserId(),
                user.getFactory().getFactoryId(),
                user.getRole().toApiValue(),
                accessToken

        );
    }

    //로그아웃
    public void logout(String accessToken) {
        tokenService.invalidate(accessToken, null); //access token 삭제
    }

    //회원가입 시 공장 처리
    private FactoryEntity resolveFactory(UserRole role, UserRequest.SignupRequest req) {
        if (role == UserRole.OWNER) {
            if (req.industry() == null || req.industry().isBlank()) {
                throw new IllegalArgumentException("owner 회원가입은 industry가 필요합니다.");
            }

            if (req.factoryName() == null || req.factoryName().isBlank()) {
                throw new IllegalArgumentException("owner 회원가입은 factoryName(공장명)이 필요합니다.");
            }
            // 업종선택하면 공장코드 생성
            String prefix = factoryCodeGenerator.industryToPrefix(req.industry());
            String factoryCode = factoryCodeGenerator.nextFactoryCode(prefix);

            // 공장 생성 (location 포함)
            FactoryEntity factory = FactoryEntity.builder()
                    .name(req.factoryName())
                    .location(req.location())
                    .build();

            // FactoryEntity에 factoryCode 필드가 있어야 set 가능
            factory.setFactoryCode(factoryCode);

            return factoryRepository.save(factory);
        }

        // worker
        if (req.factoryCode() == null || req.factoryCode().isBlank()) {
            throw new IllegalArgumentException("worker 회원가입은 factoryCode가 필요합니다.");
        }

        return factoryRepository.findByFactoryCode(req.factoryCode())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공장 코드입니다."));
    }

        // 공장별 역할 사용자 조회
        @Transactional(readOnly = true)
        public java.util.List<UserResponse.WorkerResponse> findByFactoryAndRole(Long factoryId, String role) {
        UserRole userRole = UserRole.from(role);
        return userRepository.findByFactory_FactoryIdAndRole(factoryId, userRole).stream()
            .map(user -> new UserResponse.WorkerResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getFactory().getFactoryId(),
                user.getFactory().getFactoryCode(),
                user.getImage()
            ))
            .toList();
        }

    // 사용자 이미지 수정
    @Transactional
    public UserResponse.UpdateImageResponse updateUserImage(Long userId, UserRequest.UpdateImageRequest req) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        user.updateImage(req.image());
        userRepository.save(user);
        return new UserResponse.UpdateImageResponse(user.getUserId(), user.getImage());
    }
}
