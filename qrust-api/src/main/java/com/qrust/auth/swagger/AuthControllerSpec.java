package com.qrust.auth.swagger;

import com.qrust.auth.dto.LoginRequest;
import com.qrust.auth.dto.SignUpRequest;
import com.qrust.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "QRust AUTH", description = "회원가입/로그인")
@RequestMapping("/api/v1/auth")
public interface AuthControllerSpec {

    @Operation(summary = "회원가입", description = "회원가입")
    @PostMapping("/signup")
    ApiResponse<Boolean> signup(
            @Parameter(description = "회원가입 정보")
            @RequestBody SignUpRequest request
    );

    @Operation(summary = "로그인", description = "로그인")
    @PostMapping("/login")
    ApiResponse<Boolean> login(
            @Parameter(description = "로그인 정보")
            @RequestBody LoginRequest request,
            HttpServletResponse response
    );

    @Operation(summary = "로그아웃", description = "JWT 리프레시 토큰 삭제 및 쿠키 초기화")
    @PostMapping("/logout")
    ApiResponse<Boolean> logout(
            HttpServletRequest request,
            HttpServletResponse response
    );
}
