package com.example.api.exception

open class BusinessException(
    val errorCode: ErrorCode,
    override val message: String? = errorCode.message
) : RuntimeException(message)
