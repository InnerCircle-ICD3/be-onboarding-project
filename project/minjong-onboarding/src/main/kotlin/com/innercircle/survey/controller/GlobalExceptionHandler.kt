// src/main/kotlin/com/innercircle/survey/exception/GlobalExceptionHandler.kt
package com.innercircle.survey.exception

import com.innercircle.survey.dto.ApiResponse
import com.innercircle.survey.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    // 리소스 찾을 수 없음 예외 처리
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<ApiResponse<Nothing>> {
        val errorResponse = ErrorResponse(
            code = "NOT_FOUND",
            message = ex.message ?: "Resource not found"
        )
        
        return ResponseEntity(
            ApiResponse(
                success = false,
                error = errorResponse
            ),
            HttpStatus.NOT_FOUND
        )
    }
    
    // 입력값 검증 실패 예외 처리
    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException): ResponseEntity<ApiResponse<Nothing>> {
        val errorResponse = ErrorResponse(
            code = "VALIDATION_FAILED",
            message = ex.message ?: "Validation failed"
        )
        
        return ResponseEntity(
            ApiResponse(
                success = false,
                error = errorResponse
            ),
            HttpStatus.BAD_REQUEST
        )
    }
    
    // DTO 검증 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Nothing>> {
        val errors = ex.bindingResult.fieldErrors
            .associate { it.field to (it.defaultMessage ?: "Invalid value") }
        
        val errorMessage = errors.entries.joinToString("; ") { "${it.key}: ${it.value}" }
        
        val errorResponse = ErrorResponse(
            code = "INVALID_INPUT",
            message = errorMessage
        )
        
        return ResponseEntity(
            ApiResponse(
                success = false,
                error = errorResponse
            ),
            HttpStatus.BAD_REQUEST
        )
    }
    
    // 기타 예외 처리
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ApiResponse<Nothing>> {
        val errorResponse = ErrorResponse(
            code = "INTERNAL_SERVER_ERROR",
            message = "An unexpected error occurred: ${ex.message}"
        )
        
        return ResponseEntity(
            ApiResponse(
                success = false,
                error = errorResponse
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}