package com.example.exception

open class BusinessException(
    val errorCode: ErrorCode,
    override val message: String? = errorCode.message
) : RuntimeException(message)

class InvalidSurveyRequestException(message: String) : BusinessException(ErrorCode.INVALID_INPUT, message)
class SurveyNotFoundException : BusinessException(ErrorCode.SURVEY_NOT_FOUND)
