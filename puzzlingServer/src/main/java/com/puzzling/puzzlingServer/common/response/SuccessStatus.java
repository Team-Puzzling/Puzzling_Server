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
    GET_PROJECT_MY_PUZZLE_SUCCESS(HttpStatus.OK, "개인 대시보드 퍼즐 조회 성공"),
    GET_PROJECT_TEAM_PUZZLE_SUCCESS(HttpStatus.OK, "팀 대시보드 퍼즐 조회 성공"),
    POST_PROJECT_SUCCESS(HttpStatus.CREATED, "프로젝트 등록 성공"),
    GET_PROJECT_TEAM_RANK_SUCCESS(HttpStatus.OK, "팀원 회고 랭킹 조회 성공"),
    JOIN_PROJECT_SUCCESS(HttpStatus.CREATED, "프로젝트 참여 성공"),
    GET_PROJECT_CYCLE_SUCCESS(HttpStatus.OK,"프로젝트 회고 주기 조회 성공"),

    /**
     * review
     */
    GET_REVIEW_TEMPLATE_SUCCESS(HttpStatus.OK,"회고 템플릿 목록 조회 성공"),
    POST_REVIEW_SUCCESS(HttpStatus.CREATED,"회고 글 작성 성공"),
    GET_REVIEW_PREVIOUS_TEMPLATE_SUCCESS(HttpStatus.OK, "이전 회고 템플릿 조회 성공"),
    GET_REVIEW_ACTION_PLAN_SUCCESS(HttpStatus.OK, "ACTIONPLAN 내용 조회 성공"),
    GET_REVIEW_TEAM_STATUS_SUCCESS(HttpStatus.OK, "팀원 회고 상황 조회 성공"),
    GET_PROJECT_MY_REVIEWS_SUCCESS(HttpStatus.OK, "해당 프로젝트 내 회고 리스트 조회 성공")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getStatusCode() {
        return this.httpStatus.value();
    }
}
