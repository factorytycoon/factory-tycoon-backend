// src/main/java/com/factory/tycoon/auth/AuthInterceptor.java

package com.factory.tycoon.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    public static final String REQ_ATTR_USER_ID = "AUTH_USER_ID";
    public static final Long SERVICE_ACCOUNT_ID = -1L; // 서비스간 통신용 계정
    private static final String INTERNAL_SERVICE_HEADER = "X-Internal-Service";

    private final TokenService tokenService;

    public AuthInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //Preflight는 토큰 없이 통과
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 내부 서비스 요청 체크 (Kubernetes 클러스터 내부)
        String internalService = request.getHeader(INTERNAL_SERVICE_HEADER);
        if ("backend-aws".equals(internalService) || "backend-websocket".equals(internalService)) {
            request.setAttribute(REQ_ATTR_USER_ID, SERVICE_ACCOUNT_ID);
            return true;
        }

        String auth = request.getHeader(HttpHeaders.AUTHORIZATION); //Authorization 헤더에서 토큰 추출
        if (auth == null || auth.isBlank() || !auth.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        String token = auth.substring("Bearer ".length()).trim(); //토큰 파싱
        if (!tokenService.validateAccessToken(token)) { //토큰 검증
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        Long userId = tokenService.getUserId(token); //토큰에서 userId 추출
        request.setAttribute(REQ_ATTR_USER_ID, userId); //request 속성에 userId 저장
        return true;
    }
}
