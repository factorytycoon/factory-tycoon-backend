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

            // 공장 생성
            FactoryEntity factory = FactoryEntity.builder()
                    .name(req.factoryName())
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
}
