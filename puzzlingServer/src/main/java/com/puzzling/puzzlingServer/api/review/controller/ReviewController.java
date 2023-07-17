package com.puzzling.puzzlingServer.api.review.controller;

import com.puzzling.puzzlingServer.api.review.dto.request.Review5FRequestDto;
import com.puzzling.puzzlingServer.api.review.dto.response.MyReviewProjectResponseDto;
import com.puzzling.puzzlingServer.api.review.dto.response.ReviewActionPlanResponseDto;
import com.puzzling.puzzlingServer.api.review.dto.request.ReviewAARRequestDto;
import com.puzzling.puzzlingServer.api.review.dto.response.ReviewPreviousTemplateResponseDto;

import com.puzzling.puzzlingServer.api.review.dto.response.ReviewTemplateGetResponseDto;
import com.puzzling.puzzlingServer.api.review.dto.request.ReviewTILRequestDto;
import com.puzzling.puzzlingServer.api.review.service.ReviewService;
import com.puzzling.puzzlingServer.common.response.ApiResponse;
import com.puzzling.puzzlingServer.common.response.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("review/template")
    public ApiResponse<ReviewTemplateGetResponseDto> getReviewTemplateAll() {
        return ApiResponse.success(SuccessStatus.GET_REVIEW_TEMPLATE_SUCCESS,reviewService.getReviewTemplateAll());
    }

    @PostMapping("member/{memberId}/project/{projectId}/review/TIL")
    public ApiResponse createReviewTIL(@PathVariable("memberId") Long memberId,@PathVariable("projectId") Long projectId, @Valid @RequestBody ReviewTILRequestDto reviewTILRequestDto){
        reviewService.createReviewTIL(memberId, projectId, reviewTILRequestDto);
        return ApiResponse.success(SuccessStatus.POST_REVIEW_SUCCESS.getStatusCode(), SuccessStatus.POST_REVIEW_SUCCESS.getMessage());
    }

    @PostMapping("member/{memberId}/project/{projectId}/review/5F")
    public ApiResponse createReview5F(@PathVariable("memberId") Long memberId,@PathVariable("projectId") Long projectId, @Valid @RequestBody Review5FRequestDto review5FRequestDto){
        reviewService.createReview5F(memberId, projectId, review5FRequestDto);
        return ApiResponse.success(SuccessStatus.POST_REVIEW_SUCCESS.getStatusCode(), SuccessStatus.POST_REVIEW_SUCCESS.getMessage());
    }

    @PostMapping("member/{memberId}/project/{projectId}/review/AAR")
    public ApiResponse createReviewAAR(@PathVariable("memberId") Long memberId,@PathVariable("projectId") Long projectId, @Valid @RequestBody ReviewAARRequestDto reviewAARRequestDto){
        reviewService.createReviewAAR(memberId, projectId, reviewAARRequestDto);
        return ApiResponse.success(SuccessStatus.POST_REVIEW_SUCCESS.getStatusCode(), SuccessStatus.POST_REVIEW_SUCCESS.getMessage());
    }
  
    @GetMapping("member/{memberId}/project/{projectId}/review/previous-template")
    public ApiResponse<ReviewPreviousTemplateResponseDto> getPreviousReviewTemplate(@PathVariable Long memberId, @PathVariable Long projectId) {
        return ApiResponse.success(SuccessStatus.GET_REVIEW_PREVIOUS_TEMPLATE, reviewService.getPreviousReviewTemplate(memberId, projectId));

    }

    @GetMapping("member/{memberId}/project/{projectId}/actionplan")
    public ApiResponse<ReviewActionPlanResponseDto> getReviewActionPlans(@PathVariable Long memberId, @PathVariable Long projectId) {
        return ApiResponse.success(SuccessStatus.GET_REVIEW_ACTION_PLAN, reviewService.getReviewActionPlans(memberId, projectId));
    }

    @GetMapping("member/{memberId}/project/{projectId}/review")
    public ApiResponse<MyReviewProjectResponseDto> getMyReviewProjects(@PathVariable Long memberId, @PathVariable Long projectId) {
        return ApiResponse.success(SuccessStatus.GET_MY_REVIEW_PROJECT, reviewService.getMyReviewProjects(memberId, projectId));
    }
}
