package com.hjpark.shared.exception

/** 애플리케이션의 모든 예외의 기본 클래스 */
open class CustomException(
    // 사용자에게 표시할 메시지
    open val userMessage: String,
    // 개발자를 위한 상세 메시지
    val detailMessage: String? = null,
    // 원인이 된 예외
    cause: Throwable? = null
) : RuntimeException(detailMessage ?: userMessage, cause) {
    // HTTP 상태 코드 (기본값: 500 Internal Server Error)
    open val statusCode: Int = 500
}
