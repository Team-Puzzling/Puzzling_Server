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
<<<<<<< Updated upstream
    PROJECT_VERIFY_SUCCESS(HttpStatus.OK, "초대코드 유효성 검사 성공"),
    GET_PROJECT_ALL_SUCCESS(HttpStatus.OK, "진행 중인 프로젝트 리스트 조회 성공"),
=======
    PROJECT_VERIFY_SUCCESS(HttpStatus.OK, "초대코드 유효성 검사 성공")
>>>>>>> Stashed changes
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getStatusCode() {
        return this.httpStatus.value();
    }
}
