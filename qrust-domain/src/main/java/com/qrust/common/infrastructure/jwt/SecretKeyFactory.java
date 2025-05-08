package com.qrust.common.infrastructure.jwt;

import static java.util.Base64.getEncoder;

import com.qrust.common.config.JwtConfig;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SecretKeyFactory {
    private final JwtConfig jwtConfig;

    SecretKey createSecretKey() {
        String encodedKey = getEncoder().encodeToString(jwtConfig.getJWT_SECRET().getBytes());
        return Keys.hmacShaKeyFor(encodedKey.getBytes());
    }
}
