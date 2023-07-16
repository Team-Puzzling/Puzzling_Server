package com.puzzling.puzzlingServer.api.review.service;

import com.puzzling.puzzlingServer.api.review.dto.request.Review5FRequestDto;
import com.puzzling.puzzlingServer.api.review.dto.request.ReviewAARRequestDto;
import com.puzzling.puzzlingServer.api.review.dto.response.ReviewPreviousTemplateResponseDto;

import com.puzzling.puzzlingServer.api.review.dto.response.ReviewTemplateGetResponseDto;
import com.puzzling.puzzlingServer.api.review.dto.request.ReviewTILRequestDto;


import java.util.List;

public interface ReviewService {
    List<ReviewTemplateGetResponseDto> getReviewTemplateAll();

    void createReviewTIL(Long memberId, Long projectId, ReviewTILRequestDto reviewTILRequestDto);


    void createReviewAAR(Long memberId, Long projectId, ReviewAARRequestDto reviewARRRequestDto);

    ReviewPreviousTemplateResponseDto getPreviousReviewTemplate(Long memberId, Long projectId);

}
