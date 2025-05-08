package com.qrust.auth.application;

import static com.qrust.exception.auth.ErrorMessages.EMAIL_ALREADY_EXISTS;

import com.qrust.auth.dto.SignUpRequest;
import com.qrust.auth.infrastructure.TokenService;
import com.qrust.common.exception.CustomException;
import com.qrust.common.exception.error.ErrorCode;
import com.qrust.common.infrastructure.jwt.JwtProvider;
import com.qrust.user.domain.entity.Password;
import com.qrust.user.domain.entity.User;
import com.qrust.user.domain.service.PasswordService;
import com.qrust.user.domain.service.UserService;
import com.qrust.utils.PasswordHasher;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {
    private final UserService userService;
    private final PasswordService passwordService;
    private final JwtProvider jwtProvider;
    private final TokenService tokenService;

    @Transactional
    public void signUp(SignUpRequest request) {
        if (userService.existByEmail(request.email())) {
            throw new CustomException(ErrorCode.NOT_FOUND_END_POINT, EMAIL_ALREADY_EXISTS);
        }

        User user = userService.save(User.of(request));

        String salt = PasswordHasher.generateSalt();
        String hashedPassword = PasswordHasher.hash(request.password(), salt);

        passwordService.save(Password.of(user.getId(), salt, hashedPassword));

        String at = jwtProvider.generateAccessToken(user.getId(), user.getUserRole());
        String rt = jwtProvider.generateRefreshToken(user.getId(), user.getUserRole());

        String rtKey = tokenService.getRTKey(user.getId());

        // TODO: 회원가입시 로그인까지?
        tokenService.saveRT(rtKey,rt);
    }
}
