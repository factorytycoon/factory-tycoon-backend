package com.factory.tycoon.config;

import com.factory.tycoon.auth.AuthInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//로그인 안하면 아무 API도 사용 못함
//AuthInterceptor를 스프링 MVC 체인에 등록하는 설정 파일
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    public WebMvcConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    //CORS 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                // 전체 API 인증
                .addPathPatterns("/api/v1/ft/**")

                // 인증 예외
                .excludePathPatterns(
                        "/api/v1/ft/user/signup",
                        "/api/v1/ft/user/login",
                        "/api/v1/ft/user/logout",
                        "/api/v1/ft/schedule"
                )
                
                // Swagger 인증 예외
                .excludePathPatterns(
                        "/error",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**"
                );
    }
}
