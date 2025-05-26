package com.qrust.user.dto;

public record UserResponse(
        Long id,
        String email,
        String name
) {
    public static UserResponse from(com.qrust.user.domain.entity.User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getName());
    }
}
