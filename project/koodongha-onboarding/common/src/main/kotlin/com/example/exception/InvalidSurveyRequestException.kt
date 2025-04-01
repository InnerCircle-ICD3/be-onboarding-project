package com.example.common.exception

class InvalidSurveyRequestException(message: String) : BusinessException(ErrorCode.INVALID_INPUT, message)
