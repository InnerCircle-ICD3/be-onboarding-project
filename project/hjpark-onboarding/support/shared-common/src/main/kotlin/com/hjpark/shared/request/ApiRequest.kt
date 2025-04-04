package com.hjpark.shared.request

import java.time.LocalDateTime

/** API 요청을 위한 공통 래퍼 클래스 */
data class ApiRequest<T>(
    val service: String, // 예: "survey", "question"
    val action: String,  // 예: "create", "update"
    val data: T?,        // 실제 요청 데이터
    val requestId: String = generateRequestId(service, action),
    val requestTime: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        /** 요청 ID 생성 */
        fun generateRequestId(service: String, action: String): String {
            val timestamp = LocalDateTime.now().toString().replace("[^0-9]".toRegex(), "")
            return "${service.uppercase()}_${action.uppercase()}_${timestamp}"
        }

        /** 요청 유효성 검증 (순수 함수) */
        fun <T> validate(request: ApiRequest<T>): List<String> {
            val errors = mutableListOf<String>()

            if (request.service.isBlank()) {
                errors.add("서비스명은 필수입니다")
            }

            if (request.action.isBlank()) {
                errors.add("액션은 필수입니다")
            }

            return errors
        }
    }
}
