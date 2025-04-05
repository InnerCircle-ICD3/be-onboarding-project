package me.dhlee.donghyeononboarding.common;

public record ApiResponse<T>(
    int httpCode,
    String message,
    T data
) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, null, data);
    }

    public static <T> ApiResponse<T> badRequest(String message) {
        return new ApiResponse<>(400, message, null);
    }
}
