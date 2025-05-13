package com.qrust.exception;

import com.qrust.exception.error.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String customMessage;

    public CustomException(ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage());
    }

    public CustomException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
        this.customMessage = customMessage;
    }

    @Override
    public String getMessage() {
        return customMessage;
    }
}
