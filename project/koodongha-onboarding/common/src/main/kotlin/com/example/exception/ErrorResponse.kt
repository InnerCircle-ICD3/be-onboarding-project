package com.example.exception

data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String,
    val code: String,
    val path: String
) {
    companion object {
        fun of(errorCode: ErrorCode, path: String) = ErrorResponse(
            status = errorCode.status,
            error = errorCode.error,
            message = errorCode.message,
            code = errorCode.name,
            path = path
        )

        fun of(errorCode: ErrorCode, message: String?, path: String) = ErrorResponse(
            status = errorCode.status,
            error = errorCode.error,
            message = message ?: errorCode.message,
            code = errorCode.name,
            path = path
        )
    }
}
