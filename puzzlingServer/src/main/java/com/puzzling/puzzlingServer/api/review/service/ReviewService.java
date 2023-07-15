package com.puzzling.puzzlingServer.api.review.service;

import com.puzzling.puzzlingServer.api.review.dto.response.ReviewTemplateGetResponseDto;
import com.puzzling.puzzlingServer.api.review.dto.request.ReviewTILRequestDto;


import java.util.List;

public interface ReviewService {
    List<ReviewTemplateGetResponseDto> getReviewTemplateAll();

    void createReviewTIL(Long memberId, Long projectId, ReviewTILRequestDto reviewTILRequestDto);
}