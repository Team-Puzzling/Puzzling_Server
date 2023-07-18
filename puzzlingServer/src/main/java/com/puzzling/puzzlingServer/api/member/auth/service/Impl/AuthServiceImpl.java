package com.puzzling.puzzlingServer.api.member.auth.service.Impl;

import com.puzzling.puzzlingServer.api.member.auth.SocialPlatform;
import com.puzzling.puzzlingServer.api.member.auth.dto.*;
import com.puzzling.puzzlingServer.api.member.auth.dto.Request.AuthRequestDto;
import com.puzzling.puzzlingServer.api.member.auth.dto.Response.AuthResponseDto;
import com.puzzling.puzzlingServer.api.member.auth.dto.Response.AuthTokenResponseDto;
import com.puzzling.puzzlingServer.api.member.auth.service.AuthService;
import com.puzzling.puzzlingServer.api.member.auth.service.KakaoAuthService;
import com.puzzling.puzzlingServer.api.member.domain.Member;
import com.puzzling.puzzlingServer.api.member.repository.MemberRepository;
import com.puzzling.puzzlingServer.api.project.domain.UserProject;
import com.puzzling.puzzlingServer.api.project.repository.UserProjectRepository;
import com.puzzling.puzzlingServer.common.exception.BadRequestException;
import com.puzzling.puzzlingServer.common.response.ErrorStatus;
import com.puzzling.puzzlingServer.common.config.jwt.JwtTokenProvider;
import com.puzzling.puzzlingServer.common.config.jwt.UserAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoAuthService kakaoAuthService;
    private final MemberRepository memberRepository;
    private final UserProjectRepository userProjectRepository;

    @Override
    @Transactional
    public AuthResponseDto socialLogin(String socialAccessToken, AuthRequestDto authRequestDto) throws NoSuchAlgorithmException, InvalidKeySpecException {

        if (authRequestDto.getSocialPlatform() == null) {
            throw new BadRequestException(ErrorStatus.VALIDATION_REQUEST_MISSING_EXCEPTION.getMessage());
        }

        try {
            SocialPlatform socialPlatform = SocialPlatform.valueOf(authRequestDto.getSocialPlatform());

            SocialInfoDto socialData = getSocialData(socialPlatform, socialAccessToken);

            String refreshToken = jwtTokenProvider.generateRefreshToken();

            Boolean isExistUser = isMemberBySocialId(socialData.getId());

            // 신규 유저 저장
            if (!isExistUser.booleanValue()) {
                Member member = Member.builder()
                        .name(socialData.getNickname())
                        .socialPlatform(socialPlatform)
                        .socialId(socialData.getId())
                        .build();

                memberRepository.save(member);

                member.updateRefreshToken(refreshToken);

            }
            else findMemberBySocialId(socialData.getId()).updateRefreshToken(refreshToken);

            // socialId를 통해서 등록된 유저 찾기
            Member signedMember = findMemberBySocialId(socialData.getId());

            Authentication authentication = new UserAuthentication(signedMember.getId(), null, null);

            String accessToken = jwtTokenProvider.generateAccessToken(authentication);

            String name = signedMember.getName();
            List<UserProject> userProjectList = userProjectRepository.findByMemberIdOrderByCreatedAtDesc(signedMember.getId());

            if (userProjectList.isEmpty()) {
                return AuthResponseDto.of(name, signedMember.getId(),null,
                        accessToken, refreshToken, !isExistUser);
            }
            return AuthResponseDto.of(name, signedMember.getId(), userProjectList.get(0).getProject().getId(),
                    accessToken, refreshToken, !isExistUser);

        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ErrorStatus.ANOTHER_ACCESS_TOKEN.getMessage());
        }
    }

    @Override
    @Transactional
    public AuthTokenResponseDto getNewToken(String accessToken, String refreshToken) {
        return AuthTokenResponseDto.of(accessToken,refreshToken);
    }

    private Member findMemberBySocialId(String socialId) {
        return memberRepository.findBySocialId(socialId)
                .orElseThrow(() -> new BadRequestException(ErrorStatus.INVALID_MEMBER.getMessage()));
    }

    private boolean isMemberBySocialId(String socialId) {
        return memberRepository.existsBySocialId(socialId);
    }

    private SocialInfoDto getSocialData(SocialPlatform socialPlatform, String socialAccessToken) throws NoSuchAlgorithmException, InvalidKeySpecException {

        switch (socialPlatform) {
            case KAKAO:
                return kakaoAuthService.login(socialAccessToken);
            default:
                throw new IllegalArgumentException(ErrorStatus.ANOTHER_ACCESS_TOKEN.getMessage());
        }
    }
}