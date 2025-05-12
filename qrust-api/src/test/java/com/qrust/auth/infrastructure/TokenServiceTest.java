package com.qrust.auth.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.qrust.common.config.JwtConfig;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

class TokenServiceTest {

    private StringRedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOperations;
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        redisTemplate = mock(StringRedisTemplate.class);
        valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        tokenService = new TokenService(redisTemplate);
    }

    @Test
    void refresh_token_저장_성공() {
        // given
        String key = "RT:token123";
        String userId = "1";

        // when
        tokenService.saveRT(key, userId);

        // then
        verify(valueOperations, times(1))
                .set(eq(key), eq(userId), eq(Duration.ofMillis(JwtConfig.REFRESH_TOKEN_EXPIRATION_TIME)));
    }

    @Test
    void 토큰이_유효한_경우_true() {
        // given
        String key = "RT:token123";
        String userId = "1";
        when(valueOperations.get(key)).thenReturn("1");

        // when
        boolean result = tokenService.isValid(key, userId);

        // then
        assertTrue(result);
    }

    @Test
    void 토큰이_유효하지_않는_경우_false() {
        // given
        String key = "RT:token123";
        String userId = "1";
        when(valueOperations.get(key)).thenReturn("2");

        // when
        boolean result = tokenService.isValid(key, userId);

        // then
        assertFalse(result);
    }

    @Test
    void 토큰_삭제_성공() {
        // given
        String key = "RT:token123";

        // when
        tokenService.delete(key);

        // then
        verify(redisTemplate, times(1)).delete(key);
    }

    @Test
    void 토큰으로_userId_조회_성공() {
        // given
        String token = "token123";
        String key = "RT:token123";
        when(valueOperations.get(key)).thenReturn("1");

        // when
        String result = tokenService.getUserId(token);

        // then
        assertEquals("1", result);
    }

    @Test
    void 리프레시_토큰_키_생성_성공() {
        // given
        String token = "token123";

        // when
        String result = tokenService.getRTKey(token);

        // then
        assertEquals("RT:token123", result);
    }

    @Test
    void 실제_만료시간_후_토큰_삭제됨() throws InterruptedException {
        // given
        String key = "RT:test-token";
        String userId = "123";
        Duration ttl = Duration.ofSeconds(2);
        redisTemplate.opsForValue().set(key, userId, ttl);

        // when
        Thread.sleep(3000);

        // then
        String result = redisTemplate.opsForValue().get(key);
        assertNull(result);
    }
}
