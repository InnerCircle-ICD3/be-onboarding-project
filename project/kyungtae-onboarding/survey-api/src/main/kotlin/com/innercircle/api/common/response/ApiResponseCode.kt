package com.innercircle.api.common.response

enum class ApiResponseCode(val code: String, val message: String) {
    SUCCESS("SUCCESS", "요청이 성공적으로 처리되었습니다."),
    INVALID_ARGUMENT("INVALID_ARGUMENT", "요청 파라미터가 잘못되었습니다."),
    NOT_FOUND("NOT_FOUND", "요청한 리소스를 찾을 수 없습니다."),
    INTERNAL_ERROR("INTERNAL_ERROR", "서버 오류가 발생했습니다."),
    UNAUTHORIZED("UNAUTHORIZED", "인증이 필요합니다."),
    FORBIDDEN("FORBIDDEN", "접근 권한이 없습니다.")
}