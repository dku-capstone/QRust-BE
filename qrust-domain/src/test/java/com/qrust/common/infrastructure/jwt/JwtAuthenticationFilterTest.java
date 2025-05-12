package com.qrust.common.infrastructure.jwt;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.contains;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.qrust.auth.infrastructure.TokenService;
import com.qrust.auth.service.AuthService;
import com.qrust.user.domain.entity.User;
import com.qrust.user.domain.entity.vo.LoginType;
import com.qrust.user.domain.entity.vo.UserRole;
import com.qrust.user.domain.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

class JwtAuthenticationFilterTest {

    private JwtProvider jwtProvider;
    private JwtValidator jwtValidator;
    private UserService userService;
    private TokenService tokenService;
    private AuthService authService;
    private JwtAuthenticationFilter filter;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        jwtProvider = mock(JwtProvider.class);
        jwtValidator = mock(JwtValidator.class);
        userService = mock(UserService.class);
        tokenService = mock(TokenService.class);
        authService = mock(AuthService.class);

        filter = new JwtAuthenticationFilter(jwtProvider, jwtValidator, userService, tokenService, authService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
    }

    @Test
    void access_token_유효하면_인증_적용() throws Exception {
        // given
        String accessToken = "validAccessToken";
        Cookie[] cookies = new Cookie[]{new Cookie("access_token", accessToken)};
        when(request.getCookies()).thenReturn(cookies);
        when(jwtValidator.validateToken(accessToken)).thenReturn(true);
        when(jwtValidator.getSubject(accessToken)).thenReturn("1");
        when(jwtValidator.getRole(accessToken)).thenReturn(UserRole.USER);

        // when
        filter.doFilterInternal(request, response, filterChain);

        // then
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void accessToken_만료되고_refreshToken_유효하면_재발급_후_인증_적용() throws Exception {
        // given
        String accessToken = "expiredAccessToken";
        String refreshToken = "validRefreshToken";
        String newAccessToken = "newAccessToken";
        String newRefreshToken = "newRefreshToken";

        Cookie[] cookies = new Cookie[]{
                new Cookie("access_token", accessToken),
                new Cookie("refresh_token", refreshToken)
        };
        when(request.getCookies()).thenReturn(cookies);

        when(jwtValidator.validateToken(accessToken)).thenReturn(false);
        when(jwtValidator.validateToken(refreshToken)).thenReturn(true);
        when(tokenService.getUserId(refreshToken)).thenReturn("1");
        when(tokenService.getRTKey(refreshToken)).thenReturn("RT:validRefreshToken");
        when(tokenService.isValid("RT:validRefreshToken", "1")).thenReturn(true);

        User user = User.builder()
                .email("test@example.com")
                .userName("tester")
                .userRole(UserRole.USER)
                .loginType(LoginType.EMAIL)
                .isWithdraw(false)
                .build();

        ReflectionTestUtils.setField(user, "id", 1L);
        when(userService.getById(1L)).thenReturn(user);

        when(jwtProvider.generateAccessToken(1L, UserRole.USER)).thenReturn(newAccessToken);
        when(jwtProvider.generateRefreshToken()).thenReturn(newRefreshToken);
        when(tokenService.getRTKey(newRefreshToken)).thenReturn("RT:newRefreshToken");
        when(jwtValidator.getSubject(newAccessToken)).thenReturn("1");
        when(jwtValidator.getRole(newAccessToken)).thenReturn(UserRole.USER);

        // when
        filter.doFilterInternal(request, response, filterChain);

        // then
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        verify(tokenService).saveRT(eq("RT:newRefreshToken"), eq("1"));
        verify(tokenService).delete(eq("RT:validRefreshToken"));
        verify(response, times(2)).addCookie(any());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void refresh_token도_유효하지_않으면_로그아웃_후_401반환() throws Exception {
        // given
        String accessToken = "invalid";
        String refreshToken = "invalid";
        Cookie[] cookies = new Cookie[]{
                new Cookie("access_token", accessToken),
                new Cookie("refresh_token", refreshToken)
        };
        when(request.getCookies()).thenReturn(cookies);

        when(jwtValidator.validateToken(accessToken)).thenReturn(false);
        when(jwtValidator.validateToken(refreshToken)).thenReturn(true);
        when(tokenService.getUserId(refreshToken)).thenReturn(null); // 유저 ID 없음

        // when
        filter.doFilterInternal(request, response, filterChain);

        // then
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void 쿠키_없으면_인증_부여_없음() throws Exception {
        // given
        when(request.getCookies()).thenReturn(null);

        // when
        filter.doFilterInternal(request, response, filterChain);

        // then
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void refresh_token_불일치_시_401_응답과_로그아웃() throws Exception {
        // given
        String refreshToken = "mismatchToken";
        Cookie[] cookies = new Cookie[]{new Cookie("refresh_token", refreshToken)};
        when(request.getCookies()).thenReturn(cookies);

        when(jwtValidator.validateToken(refreshToken)).thenReturn(true);
        when(tokenService.getUserId(refreshToken)).thenReturn("1");
        when(tokenService.getRTKey(refreshToken)).thenReturn("RT:mismatchToken");
        when(tokenService.isValid("RT:mismatchToken", "1")).thenReturn(false); // 불일치

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // when
        filter.doFilterInternal(request, response, filterChain);

        // then
        verify(authService).logout(response);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(writer).write(contains("INVALID_TOKEN"));
    }
}
