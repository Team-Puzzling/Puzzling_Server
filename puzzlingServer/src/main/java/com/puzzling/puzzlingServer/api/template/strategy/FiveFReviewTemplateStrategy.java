package com.puzzling.puzzlingServer.api.template.strategy;

import com.puzzling.puzzlingServer.api.review.dto.response.ReviewContentObjectDto;
import com.puzzling.puzzlingServer.api.template.domain.Review5F;

import java.util.List;

public class FiveFReviewTemplateStrategy implements ReviewTemplateStrategy<Review5F> {
    @Override
    public List<ReviewContentObjectDto> getReviewContents(Review5F review, List<ReviewContentObjectDto> reviewContent) {
        reviewContent.add(new ReviewContentObjectDto("Fact", review.getFact()));
        reviewContent.add(new ReviewContentObjectDto("Feeling", review.getFeeling()));
        reviewContent.add(new ReviewContentObjectDto("Finding", review.getFinding()));
        reviewContent.add(new ReviewContentObjectDto("Future action", review.getActionPlan()));
        reviewContent.add(new ReviewContentObjectDto("Feedback", review.getFeedback()));
        return reviewContent;
    }
}
