package com.puzzling.puzzlingServer.api.template.strategy;

import com.puzzling.puzzlingServer.api.review.domain.Review;
import com.puzzling.puzzlingServer.api.review.dto.response.ReviewContentObjectDto;

import java.util.List;

public interface ReviewTemplateStrategy<T> {
    List<ReviewContentObjectDto> getReviewContents(T review, List<ReviewContentObjectDto> reviewContent);
}
