package com.puzzling.puzzlingServer.api.member.auth.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {

    private String name;

    private Long memberId;

    private Long projectId;

    private String accessToken;

    private String refreshToken;

    private Boolean isNewUser;

    public static AuthResponseDto of (String name, Long memberId, Long projectId, String accessToken, String refreshToken, Boolean isNewUser) {
        return new AuthResponseDto(name, memberId, projectId, accessToken, refreshToken, isNewUser);
    }
}
