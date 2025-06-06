package com.qrust.auth.application;

import static com.qrust.exception.auth.ErrorMessages.EMAIL_ALREADY_EXISTS;
import static com.qrust.exception.auth.ErrorMessages.INVALID_PW;
import static com.qrust.exception.auth.ErrorMessages.WITHDRAW_USER;

import com.qrust.auth.dto.LoginRequest;
import com.qrust.auth.dto.SignUpRequest;
import com.qrust.auth.infrastructure.TokenService;
import com.qrust.auth.service.AuthService;
import com.qrust.exception.CustomException;
import com.qrust.exception.error.ErrorCode;
import com.qrust.common.infrastructure.jwt.JwtProvider;
import com.qrust.common.infrastructure.jwt.JwtValidator;
import com.qrust.user.domain.entity.Password;
import com.qrust.user.domain.entity.User;
import com.qrust.user.domain.entity.vo.UserRole;
import com.qrust.user.domain.service.PasswordService;
import com.qrust.user.domain.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {
    private final UserService userService;
    private final PasswordService passwordService;
    private final JwtProvider jwtProvider;
    private final TokenService tokenService;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpRequest request) {
        if (userService.existByEmail(request.email())) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, EMAIL_ALREADY_EXISTS);
        }

        User user = userService.save(User.of(request));

        String encodedPassword = passwordEncoder.encode(request.password());
        passwordService.save(Password.of(user.getId(), encodedPassword));

//        // TODO: 회원가입시 로그인까지?
//        String at = jwtProvider.generateAccessToken(user.getId(), user.getUserRole());
//        String rt = jwtProvider.generateRefreshToken();
//
//        String rtKey = tokenService.getRTKey(rt);
//
//        tokenService.saveRT(rtKey, user.getId().toString());
    }

    @Transactional
    public void login(LoginRequest request, HttpServletResponse response) {
        User user = userService.getByEmail(request.email());

        if (user.isWithdraw()) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, WITHDRAW_USER);
        }

        Password password = passwordService.getByUserId(user.getId());

        if (!passwordEncoder.matches(request.password(), password.getPwd())) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, INVALID_PW);
        }

        UserRole userRole = user.getUserRole();
        String accessToken = jwtProvider.generateAccessToken(user.getId(), userRole);
        String refreshToken = jwtProvider.generateRefreshToken();

        String rtKey = tokenService.getRTKey(refreshToken);
        tokenService.saveRT(rtKey, user.getId().toString());

        authService.login(accessToken, refreshToken, response);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh_token".equals(cookie.getName())) {
                    String refreshToken = cookie.getValue();
                    if (refreshToken != null) {
                        String rtKey = tokenService.getRTKey(refreshToken);
                        tokenService.delete(rtKey);
                    }
                    break;
                }
            }
        }

        authService.logout(response);
    }
}
