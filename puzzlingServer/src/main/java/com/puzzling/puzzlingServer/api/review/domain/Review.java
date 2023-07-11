package com.puzzling.puzzlingServer.api.review.domain;

import com.puzzling.puzzlingServer.api.project.domain.UserProject;
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
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_project_id")
    private UserProject userProject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_template_id")
    private ReviewTemplate reviewTemplate;

    @Column(nullable = false, name = "review_date")
    private String reviewDate;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Builder
    public Review(UserProject userProject, ReviewTemplate reviewTemplate, String reviewDate,
                  Long userId, Long projectId) {
        this.userProject = userProject;
        this.reviewTemplate = reviewTemplate;
        this.reviewDate = reviewDate;
        this.userId = userId;
        this.projectId = projectId;
    }
}
