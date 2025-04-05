package me.dhlee.donghyeononboarding.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import me.dhlee.donghyeononboarding.common.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ApiResponse<Void> handleAppException(AppException e) {
        return ApiResponse.badRequest(e.getMessage());
    }
}
