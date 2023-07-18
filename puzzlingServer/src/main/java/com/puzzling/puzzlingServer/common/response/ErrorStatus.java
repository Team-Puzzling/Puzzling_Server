package com.puzzling.puzzlingServer.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)

public enum ErrorStatus {
    /**
     * 400 BAD_REQUEST
     */
    VALIDATION_EXCEPTION("잘못된 요청입니다."),
    VALIDATION_REQUEST_MISSING_EXCEPTION("요청값이 입력되지 않았습니다."),
    VALIDATION_NAMING_EXCEPTION("이모지 및 특수기호 입력은 불가능합니다. 제외하여 입력해 주세요."),
    NO_TOKEN("토큰을 넣어주세요."),
    ANOTHER_ACCESS_TOKEN("지원하지 않는 소셜 플랫폼입니다."),
    VALIDATION_PATH_MISSING_EXCEPTION("요청하는 path에 넘겨주는 variable이 입력되지 않았습니다."),

    /**
     * 401 UNAUTHORIZED
     */
    UNAUTHORIZED_TOKEN("유효하지 않은 토큰입니다."),
    INVALID_MEMBER("유효하지 않은 유저입니다."),
    KAKAO_UNAUTHORIZED_USER("카카오 로그인 실패. 만료되었거나 잘못된 카카오 토큰입니다."),
    SIGNIN_REQUIRED("access, refreshToken 모두 만료되었습니다. 재로그인이 필요합니다."),
    VALID_ACCESS_TOKEN("아직 유효한 accessToken 입니다."),

    /**
     * 404 NOT_FOUND
     */
    NOT_FOUND_PROJECT_CODE("유효하지 않은 초대코드에요. 코드를 확인해 주세요."),
    NOT_FOUND_PROJECT("해당하는 프로젝트가 없습니다"),


    /**
     * 500 SERVER_ERROR
     */
    INTERNAL_SERVER_ERROR("예상치 못한 서버 에러가 발생했습니다."),
    BAD_GATEWAY_EXCEPTION("일시적인 에러가 발생하였습니다.\n잠시 후 다시 시도해주세요!");

    private final String message;

}