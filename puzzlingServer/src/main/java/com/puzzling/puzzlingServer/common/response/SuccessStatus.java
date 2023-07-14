package com.puzzling.puzzlingServer.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessStatus {

    /**
     * auth
     */
    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
    SIGNIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
    GET_NEW_TOKEN_SUCCESS(HttpStatus.OK,"토큰 재발급 성공"),

    /**
     * project
     */
    PROJECT_VERIFY_SUCCESS(HttpStatus.OK, "초대코드 유효성 검사 성공"),
    GET_PROJECT_ALL_SUCCESS(HttpStatus.OK, "진행 중인 프로젝트 리스트 조회 성공"),

    /**
     * review
     */
    GET_REVIEW_TEMPLATE_SUCCESS(HttpStatus.OK,"회고 템플릿 목록 조회 성공") ;
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getStatusCode() {
        return this.httpStatus.value();
    }
}
