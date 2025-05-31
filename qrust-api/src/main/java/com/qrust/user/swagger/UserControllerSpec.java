package com.qrust.user.swagger;

import com.qrust.dto.ApiResponse;
import com.qrust.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User API", description = "사용자 정보 조회 API")
public interface UserControllerSpec {
    @Operation(summary = "사용자 ID로 정보 조회")
    ApiResponse<UserResponse> getUserById(
            Long id
    );
}
