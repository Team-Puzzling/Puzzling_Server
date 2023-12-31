package com.puzzling.puzzlingServer.api.member.auth.controller;

import com.puzzling.puzzlingServer.api.member.auth.dto.Request.AuthRequestDto;
import com.puzzling.puzzlingServer.api.member.auth.dto.Response.AuthResponseDto;
import com.puzzling.puzzlingServer.api.member.auth.dto.Response.AuthTokenResponseDto;
import com.puzzling.puzzlingServer.api.member.auth.service.AuthService;
import com.puzzling.puzzlingServer.common.response.ApiResponse;
import com.puzzling.puzzlingServer.common.config.jwt.JwtTokenProvider;
import com.puzzling.puzzlingServer.common.response.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping()
    public ApiResponse<AuthResponseDto> socialLogin(@RequestHeader("Authorization") String socialAccessToken, @RequestBody AuthRequestDto authRequestDto) throws NoSuchAlgorithmException, InvalidKeySpecException {

        AuthResponseDto responseDto = authService.socialLogin(socialAccessToken, authRequestDto);
        // 로그인
        if (!responseDto.getIsNewUser()) {
            return ApiResponse.success(SuccessStatus.SIGNIN_SUCCESS, responseDto);
        }

        // 회원가입
        return ApiResponse.success(SuccessStatus.SIGNUP_SUCCESS, responseDto);
    }

    @GetMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AuthTokenResponseDto> getNewToken(HttpServletRequest request) {
        String accessToken = (String) request.getAttribute("newAccessToken");
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        return ApiResponse.success(SuccessStatus.GET_NEW_TOKEN_SUCCESS, authService.getNewToken(accessToken, refreshToken));
    }
}