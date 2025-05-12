package com.qrust.exception;

import com.qrust.dto.ApiResponse;
import com.qrust.exception.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 존재하지 않는 요청에 대한 예외
    @ExceptionHandler(value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ApiResponse<?> handleNoPageFoundException(Exception e) {
        log.error("GlobalExceptionHandler catch NoHandlerFoundException : {}", e.getMessage());
        return ApiResponse.fail(new CustomException(ErrorCode.NOT_FOUND_END_POINT));
    }

    // 유효성 검증 실패 예외
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException() in GlobalExceptionHandler : {}", e.getMessage());
        return ApiResponse.fail(new CustomException(ErrorCode.INVALID_INPUT_VALUE));
    }

    // 요청 본문 파싱 오류 예외
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ApiResponse<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("handleHttpMessageNotReadableException() in GlobalExceptionHandler : {}", e.getMessage());
        return ApiResponse.fail(new CustomException(ErrorCode.INVALID_INPUT_VALUE));
    }

    // 타입 불일치 예외
    @ExceptionHandler(value = {TypeMismatchException.class})
    public ApiResponse<?> handleTypeMismatchException(TypeMismatchException e) {
        log.error("handleTypeMismatchException() in GlobalExceptionHandler : {}", e.getMessage());
        return ApiResponse.fail(new CustomException(ErrorCode.INVALID_TYPE_VALUE));
    }

    // 커스텀 예외
    @ExceptionHandler(value = {CustomException.class})
    public ApiResponse<?> handleCustomException(CustomException e) {
        log.error("handleCustomException() in GlobalExceptionHandler throw CustomException : {}", e.getMessage());
        return ApiResponse.fail(e);
    }

    // 기본 예외
    @ExceptionHandler(value = {Exception.class})
    public ApiResponse<?> handleException(Exception e) {
        log.error("handleException() in GlobalExceptionHandler throw Exception : {}", e.getMessage());
        return ApiResponse.fail(new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
