package com.qrust.auth.dto;

public record SignUpRequest(
        String email,
        String userName,
        String password
) {
}
