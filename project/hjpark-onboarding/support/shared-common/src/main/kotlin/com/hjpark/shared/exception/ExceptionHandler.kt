package com.hjpark.shared.exception

import com.hjpark.shared.context.RequestContext
import com.hjpark.shared.response.ApiResponse
import com.sun.org.slf4j.internal.LoggerFactory

object ExceptionHandler {
    private val logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    /**
     * 예외를 ApiResponse로 변환
     */
    fun <T> handleException(ex: Throwable): ApiResponse<T> {
        val requestId = RequestContext.getRequestId() // 요청 ID 가져오기

        return when (ex) {
            is CustomException -> {
                // 개발자를 위한 상세 로깅
                logger.error(
                    "CustomException 발생: message={}, detail={}, statusCode={}",
                    ex.userMessage,
                    ex.detailMessage,
                    ex.statusCode,
                    ex.cause
                )

                ApiResponse.error(
                    code = ex.statusCode.toString(),
                    message = ex.userMessage,
                    requestId = requestId
                )
            }
            else -> {
                // 예상치 못한 예외 로깅
                logger.error("예상치 못한 예외 발생", ex)

                ApiResponse.error(
                    code = "500", // 기본 HTTP 상태 코드
                    message = "내부 서버 오류가 발생했습니다",
                    requestId = requestId
                )
            }
        }
    }
}
