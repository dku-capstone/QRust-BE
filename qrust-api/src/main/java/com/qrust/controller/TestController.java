package com.qrust.controller;

import com.qrust.common.dto.ApiResponse;
import com.qrust.common.exception.CustomException;
import com.qrust.common.exception.error.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Test API", description = "응답 포맷 및 예외 처리 테스트")
@RestController
@RequestMapping("/test")
public class TestController {

    @Operation(summary = "ResponseEntity 직접 반환")
    @GetMapping("/responseEntity")
    public ResponseEntity<String> responseEntity() {
        return ResponseEntity.ok("ok");
    }

    @Operation(summary = "성공 응답 - 메시지 포함")
    @GetMapping("/success1")
    public ApiResponse<?> successWithMessage() {
        return ApiResponse.ok("ok");
    }

    @Operation(summary = "성공 응답 - 메시지 없음")
    @GetMapping("/success2")
    public ApiResponse<?> successWithoutMessage() {
        return ApiResponse.ok(null);
    }

    @Operation(summary = "성공 응답 - 객체 반환")
    @GetMapping("/success3")
    public ApiResponse<?> successWithObject() {
        return ApiResponse.created("created");
    }

    @Operation(summary = "커스텀 예외 발생")
    @GetMapping("/exception1")
    public ApiResponse<?> testCustomException() {
        throw new CustomException(ErrorCode.TEST_ERROR);
    }

    @Operation(summary = "일반 예외 발생")
    @GetMapping("/exception2")
    public ApiResponse<?> testException() {
        throw new RuntimeException("Unhandled Exception");
    }
}
