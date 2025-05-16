package com.qrust.common.infrastructure.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.qrust.exception.CustomException;
import com.qrust.exception.error.ErrorCode;
import com.qrust.user.domain.entity.vo.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtValidatorTest {

    private JwtValidator jwtValidator;
    private SecretKeyFactory secretKeyFactory;

    private SecretKey secretKey;
    private String validToken;
    private String invalidToken = "invalid.token.value";

    @BeforeEach
    void setUp() {
        // Mock SecretKeyFactory
        secretKeyFactory = mock(SecretKeyFactory.class);

        // Generate actual secret key and token
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        when(secretKeyFactory.createSecretKey()).thenReturn(secretKey);

        // Build valid JWT
        validToken = Jwts.builder()
                .subject("123")
                .claim("role", "USER")
                .expiration(Date.from(Instant.now().plusSeconds(3600)))
                .signWith(secretKey)
                .compact();

        jwtValidator = new JwtValidator(secretKeyFactory);
    }

    @Test
    void 유효한_토큰_이면_true() {
        // when
        boolean result = jwtValidator.validateToken(validToken);

        // then
        assertTrue(result);
    }

    @Test
    void 유효하지_않은_토큰_이면_false() {
        // when
        boolean result = jwtValidator.validateToken(invalidToken);

        // then
        assertFalse(result);
    }

    @Test
    void getSubject_정상_동작() {
        // when
        String subject = jwtValidator.getSubject(validToken);

        // then
        assertEquals("123", subject);
    }

    @Test
    void getSubject_비정상_토큰이면_예외발생() {
        // when & then
        CustomException ex = assertThrows(CustomException.class, () -> jwtValidator.getSubject(invalidToken));
        assertEquals(ErrorCode.INVALID_INPUT_VALUE, ex.getErrorCode());
    }

    @Test
    void getRole_정상_동작() {
        // when
        UserRole role = jwtValidator.getRole(validToken);

        // then
        assertEquals(UserRole.USER, role);
    }

    @Test
    void getRole_비정상_토큰이면_예외발생() {
        // when & then
        CustomException ex = assertThrows(CustomException.class, () -> jwtValidator.getRole(invalidToken));
        assertEquals(ErrorCode.INVALID_INPUT_VALUE, ex.getErrorCode());
    }

    @Test
    void getExpiration_정상_동작() {
        // when
        Instant expiration = jwtValidator.getExpiration(validToken);

        // then
        assertTrue(expiration.isAfter(Instant.now()));
    }
}
