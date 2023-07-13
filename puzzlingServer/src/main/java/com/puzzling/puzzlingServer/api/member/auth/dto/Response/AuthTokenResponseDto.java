package com.puzzling.puzzlingServer.api.member.auth.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTokenResponseDto {
    private String accessToken;

    private String refreshToken;
}
