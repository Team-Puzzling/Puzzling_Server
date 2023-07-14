package com.puzzling.puzzlingServer.api.member.auth.dto.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class AuthRequestDto {
    private String socialPlatform;
}