package com.qrust.common.infrastructure.jwt;

import com.qrust.common.config.JwtConfig;
import com.qrust.user.domain.entity.vo.UserRole;
import io.jsonwebtoken.Jwts;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final SecretKeyFactory secretKeyFactory;
    private final SecretKey key = secretKeyFactory.createSecretKey();

    public String generateAccessToken(Long userId, UserRole role) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(JwtConfig.ACCESS_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .claim("role", role.name())
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(Long userId, UserRole role) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(JwtConfig.REFRESH_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .claim("role", role.name())
                .signWith(key)
                .compact();
    }
}
