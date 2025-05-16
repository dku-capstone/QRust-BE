package com.qrust.auth.application;

import static com.qrust.exception.auth.ErrorMessages.EMAIL_ALREADY_EXISTS;
import static com.qrust.exception.auth.ErrorMessages.INVALID_PW;
import static com.qrust.exception.auth.ErrorMessages.WITHDRAW_USER;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.qrust.auth.dto.LoginRequest;
import com.qrust.auth.dto.SignUpRequest;
import com.qrust.auth.infrastructure.TokenService;
import com.qrust.auth.service.AuthService;
import com.qrust.common.infrastructure.jwt.JwtProvider;
import com.qrust.exception.CustomException;
import com.qrust.user.domain.entity.Password;
import com.qrust.user.domain.entity.User;
import com.qrust.user.domain.service.PasswordService;
import com.qrust.user.domain.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthFacadeTest {

    private AuthFacade authFacade;
    private UserService userService;
    private PasswordService passwordService;
    private JwtProvider jwtProvider;
    private TokenService tokenService;
    private AuthService authService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        passwordService = mock(PasswordService.class);
        jwtProvider = mock(JwtProvider.class);
        tokenService = mock(TokenService.class);
        authService = mock(AuthService.class);
        passwordEncoder = mock(PasswordEncoder.class);

        authFacade = new AuthFacade(
                userService, passwordService, jwtProvider, tokenService, authService, passwordEncoder
        );
    }

    @Test
    void 정상_회원가입_성공() {
        // given
        SignUpRequest request = new SignUpRequest("test@email.com", "qrust", "password123");

        when(userService.existByEmail(request.email())).thenReturn(false);

        User user = mock(User.class);
        when(userService.save(any(User.class))).thenReturn(user);
        when(user.getId()).thenReturn(1L);

        String encodedPw = "encodedPassword123";
        when(passwordEncoder.encode(request.password())).thenReturn(encodedPw);

        // when
        authFacade.signUp(request);

        // then
        verify(userService).existByEmail(request.email());
        verify(userService).save(any(User.class));
        verify(passwordEncoder).encode(request.password());
    }

    @Test
    void 회원가입시_이미_존재하는_이메일이면_예외발생() {
        // given
        SignUpRequest request = new SignUpRequest("test@email.com", "qrust", "password123");
        when(userService.existByEmail(request.email())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> authFacade.signUp(request))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(EMAIL_ALREADY_EXISTS);

        verify(userService, never()).save(any());
    }

    @Test
    void 정상_로그인_성공() {
        // given
        LoginRequest request = new LoginRequest("test@email.com", "password123");

        User user = mock(User.class);
        Long userId = 1L;
        when(user.getId()).thenReturn(userId);
        when(user.getEmail()).thenReturn(request.email());
        when(user.isWithdraw()).thenReturn(false);

        Password password = Password.of(userId, "encodedPw");

        when(userService.getByEmail(request.email())).thenReturn(user);
        when(passwordService.getByUserId(userId)).thenReturn(password);
        when(passwordEncoder.matches("password123", "encodedPw")).thenReturn(true);

        when(jwtProvider.generateAccessToken(any(), any())).thenReturn("access-token");
        when(jwtProvider.generateRefreshToken()).thenReturn("refresh-token");

        when(tokenService.getRTKey("refresh-token")).thenReturn("RT:refresh-token");

        HttpServletResponse response = mock(HttpServletResponse.class);

        // when
        authFacade.login(request, response);

        // then
        verify(tokenService).saveRT(eq("RT:refresh-token"), eq(userId.toString()));
        verify(authService).login("access-token", "refresh-token", response);
    }


    @Test
    void 로그인시_탈퇴한_유저는_예외발생() {
        // given
        LoginRequest request = new LoginRequest("withdrawn@email.com", "pw123");
        User user = mock(User.class);
        when(userService.getByEmail(request.email())).thenReturn(user);
        when(user.isWithdraw()).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> authFacade.login(request, mock(HttpServletResponse.class)))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(WITHDRAW_USER);

        verify(authService, never()).login(any(), any(), any());
    }

    @Test
    void 로그인시_비밀번호_불일치_예외발생() {
        // given
        LoginRequest request = new LoginRequest("email@test.com", "wrongpw");
        User user = mock(User.class);
        Password password = mock(Password.class);

        when(userService.getByEmail(request.email())).thenReturn(user);
        when(user.isWithdraw()).thenReturn(false);
        when(user.getId()).thenReturn(1L);
        when(passwordService.getByUserId(1L)).thenReturn(password);
        when(password.getPwd()).thenReturn("encodedPw");
        when(passwordEncoder.matches("wrongpw", "encodedPw")).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> authFacade.login(request, mock(HttpServletResponse.class)))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(INVALID_PW);
    }

    @Test
    void 로그아웃시_refresh_token이_있으면_삭제됨() {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Cookie cookie = new Cookie("refresh_token", "refresh-token-value");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});

        when(tokenService.getRTKey("refresh-token-value")).thenReturn("RT:refresh-token-value");

        // when
        authFacade.logout(request, response);

        // then
        verify(tokenService).delete(eq("RT:refresh-token-value"));
        verify(authService).logout(response);
    }

    @Test
    void 로그아웃시_refresh_token이_없으면_아무것도_안함() {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getCookies()).thenReturn(null); // 쿠키 없음

        // when
        authFacade.logout(request, response);

        // then
        verify(tokenService, never()).delete(any());
        verify(authService).logout(response);
    }
}
