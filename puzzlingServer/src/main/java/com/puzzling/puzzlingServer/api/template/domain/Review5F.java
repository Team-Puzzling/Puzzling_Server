package com.puzzling.puzzlingServer.api.template.domain;

import com.puzzling.puzzlingServer.api.review.domain.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review5F {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false, length = 200)
    private String fact;

    @Column(nullable = false, length = 200)
    private String feeling;

    @Column(nullable = false, length = 200)
    private String finding;

    @Column(nullable = false, length = 200)
    private String feedback;

    @Column(nullable = false, name = "action_plan", length = 200)
    private String actionPlan;

    @Builder
    public Review5F(Review review, String fact, String feeling, String finding, String feedback, String actionPlan) {
        this.review = review;
        this.fact = fact;
        this.feeling = feeling;
        this.finding = finding;
        this.feedback = feedback;
        this.actionPlan = actionPlan;
    }
}
