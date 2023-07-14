package com.puzzling.puzzlingServer.api.review.service;

import com.puzzling.puzzlingServer.api.review.dto.ReviewTemplateGetResponseDto;
import com.puzzling.puzzlingServer.api.template.domain.ReviewTemplate;

import java.util.List;

public interface ReviewService {
    List<ReviewTemplateGetResponseDto> getReviewTemplateAll();
}
