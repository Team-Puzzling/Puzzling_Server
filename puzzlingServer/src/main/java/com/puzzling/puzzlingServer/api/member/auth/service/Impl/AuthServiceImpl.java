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
@Transactional
public class AuthServiceImpl implements AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoAuthService kakaoAuthService;
    private final MemberRepository memberRepository;

    private final UserProjectRepository userProjectRepository;

    @Override
    public AuthResponseDto socialLogin(String socialAccessToken, AuthRequestDto authRequestDto) throws NoSuchAlgorithmException, InvalidKeySpecException {

        //여기서는 email로 중복처리를 했다. 우리는 social_Id로 진행할 것이다. 그러면 우리는 여기서 social_Id를 가져오는 로직을 만들고
        //그것을 통해서 boolean isExistUser = iseMemberBySocialId로 만들어야 한다.
        //그래서 아래 코드가 수정되어야 하는데 방법이 떠오르지 않음
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
                    .refreshToken(refreshToken)
                    .build();

            memberRepository.save(member);

//            Member signedMember = findMemberBySocialId(socialId);
//            Long projectId = projectRepository.findMostRecentProjectIdByMemberId(signedMember.getId());
//            Authentication authentication = new UserAuthentication(signedMember.getId(), null, null);
//            String accessToken = jwtTokenProvider.generateAccessToken(authentication);

//            return AuthResponseDto.builder()
//                    .name(nickname)
//                    .memberId(signedMember.getId())
//                    .projectId(projectId)
//                    .accessToken(accessToken)
//                    .refreshToken(refreshToken)
//                    .isNewUser(!isExistUser)
//                    .build();
        }
        else findMemberBySocialId(socialData.getId()).updateRefreshToken(refreshToken);

        // socialId를 통해서 등록된 유저 찾기
        Member signedMember = findMemberBySocialId(socialData.getId());

        Authentication authentication = new UserAuthentication(signedMember.getId(), null, null);

        String accessToken = jwtTokenProvider.generateAccessToken(authentication);

        String name = signedMember.getName();
        System.out.println("========================예슬아 살려줘3");
        List<UserProject> userProjectList = userProjectRepository.findByMemberIdOrderByCreatedAtDesc(signedMember.getId());
        System.out.println("========================예슬아 살려줘4");

        return AuthResponseDto.of(name, signedMember.getId(), userProjectList.get(0).getProject().getId(),
                accessToken, refreshToken, !isExistUser);
    }

    @Override
    public AuthTokenResponseDto getNewToken(String accessToken, String refreshToken) {
        return AuthTokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
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
                throw new IllegalArgumentException("소셜 타입이 잘못되었습니다");
        }
    }
}
