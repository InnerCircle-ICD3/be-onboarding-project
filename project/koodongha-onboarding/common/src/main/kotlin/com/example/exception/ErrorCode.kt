package com.example.common.exception

enum class ErrorCode(
    val status: Int,
    val error: String,
    val message: String
) {
    INVALID_INPUT(400, "Bad Request", "잘못된 요청입니다."),
    SURVEY_NOT_FOUND(404, "Not Found", "설문을 찾을 수 없습니다."),
    INTERNAL_ERROR(500, "Internal Server Error", "서버 오류가 발생했습니다.")
}
