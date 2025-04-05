package com.innercircle.api.common.response

data class ApiResponse<T>(
    val message: String? = null,
    val data: T? = null,
    val code: ApiResponseCode = ApiResponseCode.SUCCESS
) {

    companion object {
        fun <T> created(data: T): ApiResponse<T> {
            return ApiResponse(data = data)
        }

        fun void(): ApiResponse<Void> {
            return ApiResponse()
        }

        fun <T> error(code: ApiResponseCode): ApiResponse<T> {
            return ApiResponse(message = code.message, code = code)
        }

        fun <T> ok(data: T): ApiResponse<T> {
            return ApiResponse(data = data)
        }
    }
}
