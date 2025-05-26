package com.qrust.user.controller;

import com.qrust.annotation.user.LoginUser;
import com.qrust.dto.ApiResponse;
import com.qrust.user.application.UserFacade;
import com.qrust.user.dto.UserResponse;
import com.qrust.user.swagger.UserControllerSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController implements UserControllerSpec {

    private final UserFacade userFacade;

    @Override
    public ApiResponse<UserResponse> getUserById(@LoginUser Long id) {
        return ApiResponse.ok(userFacade.getUserById(id));
    }
}
