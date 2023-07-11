package com.puzzling.puzzlingServer.api.template.domain;

import com.puzzling.puzzlingServer.api.review.domain.Review;
import com.puzzling.puzzlingServer.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewAAR extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false, name = "initial_goal", length = 200)
    private String initialGoal;

    @Column(nullable = false, length = 200)
    private String result;

    @Column(nullable = false, length = 200)
    private String difference;

    @Column(nullable = false, length = 200)
    private String persistence;

    @Column(nullable = false, name = "action_plan", length = 200)
    private String actionPlan;

    @Builder
    public ReviewAAR(Review review, String initialGoal, String result, String difference, String persistence, String actionPlan) {
        this.review = review;
        this.initialGoal = initialGoal;
        this.result = result;
        this.difference = difference;
        this.persistence = persistence;
        this.actionPlan = actionPlan;
    }
}
