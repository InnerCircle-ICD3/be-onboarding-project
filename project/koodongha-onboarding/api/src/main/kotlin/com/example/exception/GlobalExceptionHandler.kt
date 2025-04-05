package com.example.exception

import com.example.exception.*
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.bind.MethodArgumentNotValidException

@ControllerAdvice
class GlobalExceptionHandler {

    private val log: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.warn("BusinessException 발생: code=${e.errorCode}, message=${e.message}")
        val response = ErrorResponse.of(e.errorCode, e.message, request.requestURI)
        return ResponseEntity.status(e.errorCode.status).body(response)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(e: IllegalArgumentException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.warn("잘못된 요청 파라미터: ${e.message}")
        val response = ErrorResponse.of(ErrorCode.INVALID_INPUT, e.message, request.requestURI)
        return ResponseEntity.badRequest().body(response)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleTypeMismatch(e: MethodArgumentTypeMismatchException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.warn("파라미터 타입 불일치: ${e.message}")
        val response = ErrorResponse.of(ErrorCode.INVALID_INPUT, e.message, request.requestURI)
        return ResponseEntity.badRequest().body(response)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorMessage = e.bindingResult.fieldErrors
            .joinToString(", ") { "${it.field}: ${it.defaultMessage}" }
        log.warn("요청 값 검증 실패: $errorMessage")
        val response = ErrorResponse.of(ErrorCode.INVALID_INPUT, errorMessage, request.requestURI)
        return ResponseEntity.badRequest().body(response)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(e: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error("Unhandled Exception 발생!", e)
        val response = ErrorResponse.of(ErrorCode.INTERNAL_ERROR, e.message, request.requestURI)
        return ResponseEntity.status(ErrorCode.INTERNAL_ERROR.status).body(response)
    }
}
