package com.qrust.common.dto;

import com.qrust.common.exception.error.ErrorCode;
import jakarta.validation.constraints.NotNull;

public record ExceptionDto(
        @NotNull Integer code,
        @NotNull String message
) {
    public static ExceptionDto of(ErrorCode errorCode) {
        return new ExceptionDto(errorCode.getCode(), errorCode.getMessage());
    }
}
