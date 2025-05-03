package com.qrust.common.exception;

import com.qrust.common.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public String getMessage() {
        return errorCode.getMessage();
    }
}
