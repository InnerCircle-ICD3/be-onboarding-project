package com.example.api.exception

class InvalidSurveyRequestException(message: String) : BusinessException(ErrorCode.INVALID_INPUT, message)
