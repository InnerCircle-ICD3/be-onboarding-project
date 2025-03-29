package com.innercircle.api.common

data class ApiResponse<T>(
    val message: String? = null,
    val data: T? = null,
    val status: Int = 200
) {

    companion object {
        fun <T> created(data: T): ApiResponse<T> {
            return ApiResponse(data = data, status = 201)
        }

        fun void(): ApiResponse<Void> {
            return ApiResponse()
        }

        fun <T> error(message: String): ApiResponse<T> {
            return ApiResponse(message = message)
        }
    }
}
