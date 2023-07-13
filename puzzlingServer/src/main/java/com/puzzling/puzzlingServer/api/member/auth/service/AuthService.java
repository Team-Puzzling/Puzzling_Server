package com.puzzling.puzzlingServer.api.member.auth.service;

import com.puzzling.puzzlingServer.api.member.auth.dto.Request.AuthRequestDto;
import com.puzzling.puzzlingServer.api.member.auth.dto.Response.AuthResponseDto;
import com.puzzling.puzzlingServer.api.member.auth.dto.Response.AuthTokenResponseDto;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface AuthService {
    //AuthResponseDto socialLogin(AuthRequestDto authRequestDto) throws NoSuchAlgorithmException, InvalidKeySpecException;

    AuthResponseDto socialLogin(String socialAccessToken, AuthRequestDto authRequestDto) throws NoSuchAlgorithmException, InvalidKeySpecException;

    AuthTokenResponseDto getNewToken(String accessToken, String refreshToken);
}
