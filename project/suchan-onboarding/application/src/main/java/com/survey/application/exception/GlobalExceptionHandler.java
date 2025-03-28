package com.survey.application.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        bindFieldError(ex, errors);

        bindObjectError(ex, errors);

        logger.error("validation error : {}", errors);
        return new ResponseEntity<>(new ErrorResponse("validation error", errors), HttpStatus.BAD_REQUEST);
    }

    private void bindFieldError(MethodArgumentNotValidException ex, Map<String, String> errors) {
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
    }

    private void bindObjectError(MethodArgumentNotValidException ex, Map<String, String> errors) {
        ex.getBindingResult().getGlobalErrors().forEach((error) -> {
            String objectName = error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            errors.put(objectName, errorMessage);
        });
    }

    record ErrorResponse(String errorMessage, Map<String, String> errors, LocalDateTime timestamp) {
        ErrorResponse(String errorMessage) {
            this(errorMessage, null, LocalDateTime.now());
        }

        ErrorResponse(String errorMessage, Map<String, String> errors) {
            this(errorMessage, errors, LocalDateTime.now());
        }
    }
}
