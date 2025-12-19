package com.factory.tycoon.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProps(
        String secret,
        long accessTtlMs, //access 토큰 만료
        long refreshTtlMs //refresh 토큰 만료
) {
}
