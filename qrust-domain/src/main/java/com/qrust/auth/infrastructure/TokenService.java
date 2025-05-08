package com.qrust.auth.infrastructure;

import com.qrust.common.infrastructure.jwt.JwtValidator;
import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtValidator jwtValidator;
    private final StringRedisTemplate redisTemplate;

    private static final String RT_PREFIX = "RT:";

    public void saveRT(String key, String token) {
        Instant expiry = jwtValidator.getExpiration(token);
        Duration duration = Duration.between(Instant.now(), expiry);
        redisTemplate.opsForValue().set(key, token, duration);
    }

    public boolean isValid(String key, String token) {
        String stored = redisTemplate.opsForValue().get(key);
        return token.equals(stored);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public String getRTKey(Long userId) {
        return RT_PREFIX + userId;
    }
}
