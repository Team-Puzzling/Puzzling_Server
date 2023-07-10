package com.puzzling.puzzlingServer.common.response;

import lombok.Builder;

@Builder
public class ApiResponse<T> {

    private final int status;
    private final boolean success;
    private final String message;
    private T data;

    public static ApiResponse success(int status, String message, Object data) {
            return ApiResponse.builder()
            .status(status)
            .success(true)
            .message(message)
            .data(data)
            .build();
            }

    public static ApiResponse success(int status, String message) {
            return ApiResponse.builder()
            .status(status)
            .success(true)
            .message(message)
            .build();
            }

    public static ApiResponse fail(int status, String message) {
            return ApiResponse.builder()
            .status(status)
            .success(false)
            .message(message)
            .build();
            }
}
