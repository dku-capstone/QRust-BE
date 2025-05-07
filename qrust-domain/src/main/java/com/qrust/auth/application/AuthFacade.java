package com.qrust.auth.application;

import static com.qrust.exception.auth.ErrorMessages.EMAIL_ALREADY_EXISTS;

import com.qrust.auth.dto.SignUpRequest;
import com.qrust.common.exception.CustomException;
import com.qrust.common.exception.error.ErrorCode;
import com.qrust.user.domain.entity.User;
import com.qrust.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFacade {
    private final UserService userService;

    public void signUp(SignUpRequest request) {
        if (userService.existByEmail(request.email())) {
            throw new CustomException(ErrorCode.NOT_FOUND_END_POINT, EMAIL_ALREADY_EXISTS);
        }

        User user = userService.save(User.of(request));
    }
}
