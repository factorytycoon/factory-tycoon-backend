package com.factory.tycoon.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service
public class TokenService {

    private final JwtProps props;
    private final RedisTemplate<String, String> redisTemplate;

    public TokenService(JwtProps props, @Qualifier("redisTemplate") RedisTemplate<String, String> redisTemplate) { 
        this.props = props;
        this.redisTemplate = redisTemplate;
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(props.secret().getBytes(StandardCharsets.UTF_8));
    }

    private String accessKey(String token)  { return "access:"  + token; }
    private String refreshKey(String token) { return "refresh:" + token; } 

    // access/refresh 토큰 생성 및 Redis 저장
    public String createAccessToken(Long userId, String role) { 
        long now = System.currentTimeMillis();

        String token = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("role", role)         // owner/worker
                .claim("type", "access")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + props.accessTtlMs()))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();

        // Redis에 토큰 저장 및 만료시간 설정
        redisTemplate.opsForValue().set(accessKey(token), String.valueOf(userId),
                props.accessTtlMs(), TimeUnit.MILLISECONDS);

        return token;
    }

    // refresh 토큰 생성 및 Redis 저장
    public String createRefreshToken(Long userId) {
        long now = System.currentTimeMillis();

        // JWT 생성
        String token = Jwts.builder() 
                .setSubject(String.valueOf(userId))
                .claim("type", "refresh")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + props.refreshTtlMs()))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();

        redisTemplate.opsForValue().set(refreshKey(token), String.valueOf(userId),
                props.refreshTtlMs(), TimeUnit.MILLISECONDS);

        return token;
    }

    // access 토큰 검증
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return redisTemplate.opsForValue().get(accessKey(token)) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    // 토큰 무효화(로그아웃)
    public void invalidate(String accessToken, String refreshToken) {
        if (accessToken != null && !accessToken.isBlank()) redisTemplate.delete(accessKey(accessToken));
        if (refreshToken != null && !refreshToken.isBlank()) redisTemplate.delete(refreshKey(refreshToken));
    }
}