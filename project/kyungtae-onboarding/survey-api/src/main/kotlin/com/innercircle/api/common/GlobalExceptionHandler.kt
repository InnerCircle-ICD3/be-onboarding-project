package com.innercircle.api.common

import com.innercircle.api.common.response.ApiResponse
import com.innercircle.api.common.response.ApiResponseCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler : Log {

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<ApiResponse<Unit>> {
        log.error("Bad request", ex)
        return ResponseEntity.ok().body(ApiResponse.error(ApiResponseCode.INVALID_ARGUMENT))
    }

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(ex: NoSuchElementException): ResponseEntity<ApiResponse<Unit>> {
        log.error("Resource not found", ex)
        return ResponseEntity.ok().body(ApiResponse.error(ApiResponseCode.NOT_FOUND))
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGeneric(ex: Exception): ResponseEntity<ApiResponse<Unit>> {
        log.error("Internal server error", ex)
        return ResponseEntity.ok().body(ApiResponse.error(ApiResponseCode.INTERNAL_ERROR))
    }

}