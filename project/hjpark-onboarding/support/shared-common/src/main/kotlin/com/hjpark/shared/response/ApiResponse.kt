package com.hjpark.shared.response

import java.time.LocalDateTime

/** API 응답을 위한 공통 래퍼 클래스 */
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: Error? = null,
    val requestId: String? = null,
    val responseTime: LocalDateTime = LocalDateTime.now()
) {
    /** 오류 정보 */
    data class Error(
        val code: String,
        val message: String
    )

    companion object {
        // 성공 응답 (데이터 포함)
        fun <T> success(data: T, requestId: String? = null): ApiResponse<T> {
            return ApiResponse(
                success = true,
                data = data,
                requestId = requestId
            )
        }

        // 성공 응답 (데이터 없음)
        fun success(requestId: String? = null): ApiResponse<Unit> {
            return ApiResponse(
                success = true,
                data = null,
                requestId = requestId
            )
        }

        // 에러 응답
        fun <T> error(code: String, message: String, requestId: String? = null): ApiResponse<T> {
            return ApiResponse(
                success = false,
                error = Error(code, message),
                requestId = requestId
            )
        }
    }
}
