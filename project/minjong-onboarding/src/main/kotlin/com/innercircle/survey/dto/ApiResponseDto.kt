// src/main/kotlin/com/innercircle/survey/dto/ApiResponseDto.kt
package com.innercircle.survey.dto

// API 응답 공통 포맷 DTO
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: ErrorResponse? = null
)

// 에러 응답 DTO
data class ErrorResponse(
    val code: String,
    val message: String
)