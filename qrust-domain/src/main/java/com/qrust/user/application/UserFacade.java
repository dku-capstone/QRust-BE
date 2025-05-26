package com.qrust.user.application;

import com.qrust.user.domain.service.UserService;
import com.qrust.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;

    public UserResponse getUserById(Long id) {
        return UserResponse.from(userService.getById(id));
    }
}
