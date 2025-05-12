package com.qrust.common.infrastructure.jwt;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.qrust.user.domain.entity.vo.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JwtProviderTest {

    @Mock
    private SecretKeyFactory secretKeyFactory;

    @InjectMocks
    private JwtProvider jwtProvider;

    @Test
    void accessToken_생성_시_정상적으로_claim_포함() {
        // given
        Long userId = 1L;
        UserRole role = UserRole.USER;
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        given(secretKeyFactory.createSecretKey()).willReturn(secretKey);

        // when
        String token = jwtProvider.generateAccessToken(userId, role);

        // then
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals(userId.toString(), claims.getSubject());
        assertEquals(role.name(), claims.get("role"));
    }

    @Test
    void refreshToken_생성_시_UUID형식으로_반환() {
        // when
        String refreshToken = jwtProvider.generateRefreshToken();

        // then
        assertDoesNotThrow(() -> UUID.fromString(refreshToken));
    }
}
