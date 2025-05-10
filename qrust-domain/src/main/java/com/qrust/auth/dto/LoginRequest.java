package com.qrust.auth.dto;

public record LoginRequest(
        String email,
        String password
) {
}
