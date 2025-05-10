package com.qrust.common.infrastructure.jwt;

import static com.qrust.exception.auth.ErrorMessages.INVALID_TOKEN;

import com.qrust.common.exception.CustomException;
import com.qrust.common.exception.error.ErrorCode;
import com.qrust.user.domain.entity.vo.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtValidator {
    private final SecretKeyFactory secretKeyFactory;

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKeyFactory.createSecretKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getSubject(String token) {
        try {
            return Jwts.parser().verifyWith(secretKeyFactory.createSecretKey()).build().parseSignedClaims(token)
                    .getPayload().getSubject();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, INVALID_TOKEN);
        }
    }

    public UserRole getRole(String token) {
        try {
            Claims claims = Jwts.parser().verifyWith(secretKeyFactory.createSecretKey()).build()
                    .parseSignedClaims(token).getPayload();
            String roleRaw = claims.get("role", String.class);

            return UserRole.valueOf(roleRaw);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, INVALID_TOKEN);
        }
    }

    public Instant getExpiration(String token) {
        return Jwts.parser().verifyWith(secretKeyFactory.createSecretKey()).build().parseSignedClaims(token)
                .getPayload().getExpiration().toInstant();
    }
}
