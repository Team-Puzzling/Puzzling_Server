package com.puzzling.puzzlingServer.api.review.service;

import com.puzzling.puzzlingServer.api.review.dto.request.Review5FRequestDto;
import com.puzzling.puzzlingServer.api.review.dto.response.*;
import com.puzzling.puzzlingServer.api.review.dto.request.ReviewAARRequestDto;

import com.puzzling.puzzlingServer.api.review.dto.request.ReviewTILRequestDto;


import java.util.List;

public interface ReviewService {
    List<ReviewTemplateGetResponseDto> getReviewTemplateAll();

    ReviewPostResponseDto createReviewTIL(Long memberId, Long projectId, ReviewTILRequestDto reviewTILRequestDto);

    ReviewPostResponseDto createReviewAAR(Long memberId, Long projectId, ReviewAARRequestDto reviewARRRequestDto);

    ReviewPostResponseDto createReview5F(Long memberId, Long projectId, Review5FRequestDto review5FRequestDto);

    ReviewPreviousTemplateResponseDto getPreviousReviewTemplate(Long memberId, Long projectId);

    List<ReviewActionPlanResponseDto> getReviewActionPlans(Long memberId, Long projectId);

    ReviewTeamStatusResponseDto getTeamReviewStatus(Long projectId, String startDate, String endDate);

    List<MyReviewProjectResponseDto> getMyReviewProjects(Long memberId, Long projectId);

    ReviewDetailResponseDto getMyReviewDetail(Long memberId, Long projectId, String startDate, String endDate);

}
