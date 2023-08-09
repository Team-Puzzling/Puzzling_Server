package com.puzzling.puzzlingServer.api.template.strategy;

import com.puzzling.puzzlingServer.api.review.dto.response.objectDto.ReviewContentObjectDto;
import com.puzzling.puzzlingServer.api.template.domain.ReviewTIL;

import java.util.List;

public class TILReviewTemplateStrategy implements ReviewTemplateStrategy<ReviewTIL> {
    @Override
    public List<ReviewContentObjectDto> getReviewContents(ReviewTIL review, List<ReviewContentObjectDto> reviewContent) {
        reviewContent.add(new ReviewContentObjectDto("잘한 점", review.getLiked()));
        reviewContent.add(new ReviewContentObjectDto("아쉬운 점", review.getLacked()));
        reviewContent.add(new ReviewContentObjectDto("배운 점", review.getActionPlan()));
        return reviewContent;
    }

}
