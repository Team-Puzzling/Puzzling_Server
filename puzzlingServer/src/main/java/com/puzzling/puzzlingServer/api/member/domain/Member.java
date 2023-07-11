package com.puzzling.puzzlingServer.api.member.domain;

import com.puzzling.puzzlingServer.api.member.auth.SocialPlatform;
import com.puzzling.puzzlingServer.common.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "social_platform")
    private SocialPlatform socialPlatform;

    @Column(nullable = false, name = "social_id")
    private String socialId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder
    public Member(String name, SocialPlatform socialPlatform, String socialId) {
        this.name = name;
        this.socialId = socialId;
        this.socialPlatform = socialPlatform;
    }

    public void updateRefreshToken (String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
