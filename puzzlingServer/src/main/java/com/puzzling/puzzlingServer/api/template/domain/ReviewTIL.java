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
public class ReviewTIL extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false, length = 200)
    private String liked;

    @Column(nullable = false, length = 200)
    private String lacked;

    @Column(nullable = false, name = "action_plan", length = 200)
    private String actionPlan;

    @Builder
    public ReviewTIL(Review review, String liked, String lacked, String actionPlan) {
        this.review = review;
        this.liked = liked;
        this.lacked = lacked;
        this.actionPlan = actionPlan;
    }
}
