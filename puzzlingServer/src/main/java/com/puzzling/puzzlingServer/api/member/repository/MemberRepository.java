package com.puzzling.puzzlingServer.api.member.repository;

import com.puzzling.puzzlingServer.api.member.auth.SocialPlatform;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.jpa.repository.JpaRepository;
import com.puzzling.puzzlingServer.api.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long id);

    boolean existsBySocialId(String socialId);

    boolean existsMemberBySocialIdAndSocialPlatform(String socialId, SocialPlatform socialPlatform);

    Optional<Member> getMemberBySocialIdAndSocialPlatform(String socialId, SocialPlatform socialPlatform);
    Optional<Member> findByIdAndRefreshToken(Long memberId, String refreshToken);

    Optional<Member> findBySocialId(String socialId);

    Long getMemberIdBySocialId(String socialId);
}