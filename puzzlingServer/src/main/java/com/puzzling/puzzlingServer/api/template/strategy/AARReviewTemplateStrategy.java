package com.puzzling.puzzlingServer.api.template.strategy;

import com.puzzling.puzzlingServer.api.review.dto.response.ReviewContentObjectDto;
import com.puzzling.puzzlingServer.api.template.domain.ReviewAAR;

import java.util.List;

public class AARReviewTemplateStrategy implements ReviewTemplateStrategy<ReviewAAR> {
    @Override
    public List<ReviewContentObjectDto> getReviewContents(ReviewAAR review, List<ReviewContentObjectDto> reviewContent) {
        reviewContent.add(new ReviewContentObjectDto("초기의 목표", review.getInitialGoal()));
        reviewContent.add(new ReviewContentObjectDto("결과", review.getResult()));
        reviewContent.add(new ReviewContentObjectDto("차이가 발생한 이유", review.getDifference()));
        reviewContent.add(new ReviewContentObjectDto("지속하고 싶은 점", review.getPersistence()));
        reviewContent.add(new ReviewContentObjectDto("앞으로의 목적", review.getActionPlan()));
        return reviewContent;
    }
}
