package com.puzzling.puzzlingServer.api.member.auth.dto.Response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenResponseDto {
    private String accessToken;

    private String refreshToken;
    public static AuthTokenResponseDto of (String accessToken, String refreshToken) {
        return new AuthTokenResponseDto(accessToken, refreshToken);
    }
}
