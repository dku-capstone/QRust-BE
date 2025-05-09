package com.qrust.auth.application;

import static com.qrust.exception.auth.ErrorMessages.EMAIL_ALREADY_EXISTS;
import static com.qrust.exception.auth.ErrorMessages.INVALID_PW;
import static com.qrust.exception.auth.ErrorMessages.WITHDRAW_USER;

import com.qrust.auth.dto.LoginRequest;
import com.qrust.auth.dto.SignUpRequest;
import com.qrust.auth.infrastructure.TokenService;
import com.qrust.auth.service.AuthService;
import com.qrust.common.exception.CustomException;
import com.qrust.common.exception.error.ErrorCode;
import com.qrust.common.infrastructure.jwt.JwtProvider;
import com.qrust.user.domain.entity.Password;
import com.qrust.user.domain.entity.User;
import com.qrust.user.domain.entity.vo.UserRole;
import com.qrust.user.domain.service.PasswordService;
import com.qrust.user.domain.service.UserService;
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
            throw new CustomException(ErrorCode.NOT_FOUND_END_POINT, EMAIL_ALREADY_EXISTS);
        }

        User user = userService.save(User.of(request));

        String encodedPassword = passwordEncoder.encode(request.password());
        passwordService.save(Password.of(user.getId(), encodedPassword));

        String at = jwtProvider.generateAccessToken(user.getId(), user.getUserRole());
        String rt = jwtProvider.generateRefreshToken(user.getId(), user.getUserRole());

        String rtKey = tokenService.getRTKey(user.getId());

        // TODO: 회원가입시 로그인까지?
        tokenService.saveRT(rtKey, rt);
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
        String refreshToken = jwtProvider.generateRefreshToken(user.getId(), userRole);

        String rtKey = tokenService.getRTKey(user.getId());
        tokenService.saveRT(rtKey, refreshToken);

        authService.login(accessToken, refreshToken, response);
    }
}
