package com.innercircle.api.common.response

data class ApiResponseDto<T>(
    val message: String? = null,
    val data: T? = null,
    val code: ApiResponseCode = ApiResponseCode.SUCCESS
) {

    companion object {
        fun <T> created(data: T): ApiResponseDto<T> {
            return ApiResponseDto(data = data)
        }

        fun void(): ApiResponseDto<Void> {
            return ApiResponseDto()
        }

        fun <T> error(code: ApiResponseCode): ApiResponseDto<T> {
            return ApiResponseDto(message = code.message, code = code)
        }

        fun <T> ok(data: T): ApiResponseDto<T> {
            return ApiResponseDto(data = data)
        }
    }
}
