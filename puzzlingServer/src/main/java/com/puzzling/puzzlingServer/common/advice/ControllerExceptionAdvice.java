package com.puzzling.puzzlingServer.common.advice;

import com.puzzling.puzzlingServer.common.exception.BaseException;
import com.puzzling.puzzlingServer.common.response.ApiResponse;
import com.puzzling.puzzlingServer.common.response.ErrorStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;

import static com.puzzling.puzzlingServer.common.response.ErrorStatus.KAKAO_UNAUTHORIZED_USER;
import static com.puzzling.puzzlingServer.common.response.ErrorStatus.VALIDATION_PATH_MISSING_EXCEPTION;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse> handleGlobalException(BaseException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(ApiResponse.fail(ex.getStatusCode(), ex.getResponseMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse> handleMissingParameter(MissingServletRequestParameterException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(HttpStatus.BAD_REQUEST.value(),
                        ErrorStatus.VALIDATION_REQUEST_MISSING_EXCEPTION.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse>  handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ApiResponse>  handleDateTimeParseException(DateTimeParseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), "요청하는 날짜 형식이 올바르지 않습니다."));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ApiResponse>  handleHttpClientErrorException(HttpClientErrorException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.fail(HttpStatus.UNAUTHORIZED.value(), KAKAO_UNAUTHORIZED_USER.getMessage()));
    }

}
