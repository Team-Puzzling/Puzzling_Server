package com.puzzling.puzzlingServer.api.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.puzzling.puzzlingServer.api.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long id);

    boolean existsBySocialId(String socialId);
    Optional<Member> findByIdAndRefreshToken(Long memberId, String refreshToken);

    Optional<Member> findBySocialId(String socialId);
}