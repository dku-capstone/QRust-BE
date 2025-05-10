package com.qrust.auth.infrastructure;

import com.qrust.common.config.JwtConfig;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final StringRedisTemplate redisTemplate;

    private static final String RT_PREFIX = "RT:";

    public void saveRT(String key, String userId) {
        Duration duration = Duration.ofMillis(JwtConfig.REFRESH_TOKEN_EXPIRATION_TIME);
        redisTemplate.opsForValue().set(key, userId, duration);
    }

    public boolean isValid(String key, String userId) {
        String stored = redisTemplate.opsForValue().get(key);
        return userId.equals(stored);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public String getUserId(String refreshToken) {
        String key = getRTKey(refreshToken);
        return redisTemplate.opsForValue().get(key);
    }

    public String getRTKey(String token) {
        return RT_PREFIX + token;
    }

}
