package com.innercircle.api.common

import com.innercircle.api.common.response.ApiResponseCode
import com.innercircle.api.common.response.ApiResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler : Log {

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<ApiResponseDto<Any>> {
        log.error("Bad request", ex)
        return ResponseEntity.ok().body(ApiResponseDto.error(ApiResponseCode.INVALID_ARGUMENT))
    }

    @ExceptionHandler(NoSuchElementException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(ex: NoSuchElementException): ResponseEntity<ApiResponseDto<Any>> {
        log.error("Resource not found", ex)
        return ResponseEntity.ok().body(ApiResponseDto.error(ApiResponseCode.NOT_FOUND))
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGeneric(ex: Exception): ResponseEntity<ApiResponseDto<Any>> {
        log.error("Internal server error", ex)
        return ResponseEntity.ok().body(ApiResponseDto.error(ApiResponseCode.INTERNAL_ERROR))
    }

}