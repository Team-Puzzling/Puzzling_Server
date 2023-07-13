package com.puzzling.puzzlingServer.api.project.domain;

import com.puzzling.puzzlingServer.api.member.domain.Member;
import com.puzzling.puzzlingServer.api.template.domain.ReviewTemplate;
import com.puzzling.puzzlingServer.common.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class UserProject extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String role;

    @Column(nullable = false, length = 10)
    private String nickname;

    @Column(name = "leader_or_not", nullable = false)
    private Boolean leaderOrNot;

    @Column(name = "previous_template_id")
    private Long reviewTemplateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Builder
    public UserProject(String role, String nickname, Boolean leaderOrNot, Long reviewTemplateId,
                       Member member, Project project) {
        this.role = role;
        this.nickname = nickname;
        this.leaderOrNot = leaderOrNot;
        this.reviewTemplateId = reviewTemplateId;
        this.member = member;
        this.project = project;
    }
}
