package com.qrust.auth.controller;

import com.qrust.auth.application.AuthFacade;
import com.qrust.auth.dto.LoginRequest;
import com.qrust.auth.dto.SignUpRequest;
import com.qrust.auth.swagger.AuthControllerSpec;
import com.qrust.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerSpec {
    private final AuthFacade authFacade;

    @Override
    public ApiResponse<Boolean> signup(@RequestBody SignUpRequest request) {
        authFacade.signUp(request);
        return ApiResponse.ok(true);
    }

    @Override
    public ApiResponse<Boolean> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        authFacade.login(request, response);
        return ApiResponse.ok(true);
    }

    @Override
    public ApiResponse<Boolean> logout(HttpServletRequest request, HttpServletResponse response) {
        authFacade.logout(request, response);
        return ApiResponse.ok(true);
    }
}
