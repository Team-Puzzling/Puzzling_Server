package com.puzzling.puzzlingServer.api.review.controller;

import com.puzzling.puzzlingServer.api.review.dto.request.Review5FRequestDto;
import com.puzzling.puzzlingServer.api.review.dto.response.*;
import com.puzzling.puzzlingServer.api.review.dto.request.ReviewAARRequestDto;

import com.puzzling.puzzlingServer.api.review.dto.request.ReviewTILRequestDto;
import com.puzzling.puzzlingServer.api.review.service.ReviewService;
import com.puzzling.puzzlingServer.common.response.ApiResponse;
import com.puzzling.puzzlingServer.common.response.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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
    public ApiResponse<ReviewPostResponseDto> createReviewTIL(@PathVariable("memberId") Long memberId,@PathVariable("projectId") Long projectId, @Valid @RequestBody ReviewTILRequestDto reviewTILRequestDto){
        return ApiResponse.success(SuccessStatus.POST_REVIEW_SUCCESS, reviewService.createReviewTIL(memberId, projectId, reviewTILRequestDto));
    }

    @PostMapping("member/{memberId}/project/{projectId}/review/5F")
    public ApiResponse<ReviewPostResponseDto> createReview5F(@PathVariable("memberId") Long memberId,@PathVariable("projectId") Long projectId, @Valid @RequestBody Review5FRequestDto review5FRequestDto){
        return ApiResponse.success(SuccessStatus.POST_REVIEW_SUCCESS, reviewService.createReview5F(memberId, projectId, review5FRequestDto));
    }

    @PostMapping("member/{memberId}/project/{projectId}/review/AAR")
    public ApiResponse<ReviewPostResponseDto> createReviewAAR(@PathVariable("memberId") Long memberId,@PathVariable("projectId") Long projectId, @Valid @RequestBody ReviewAARRequestDto reviewAARRequestDto){
        return ApiResponse.success(SuccessStatus.POST_REVIEW_SUCCESS, reviewService.createReviewAAR(memberId, projectId, reviewAARRequestDto));
    }
  
    @GetMapping("member/{memberId}/project/{projectId}/review/previous-template")
    public ApiResponse<ReviewPreviousTemplateResponseDto> getPreviousReviewTemplate(@PathVariable Long memberId, @PathVariable Long projectId) {
        return ApiResponse.success(SuccessStatus.GET_REVIEW_PREVIOUS_TEMPLATE_SUCCESS, reviewService.getPreviousReviewTemplate(memberId, projectId));

    }

    @GetMapping("member/{memberId}/project/{projectId}/actionplan")
    public ApiResponse<ReviewActionPlanResponseDto> getReviewActionPlans(@PathVariable Long memberId, @PathVariable Long projectId) {
        return ApiResponse.success(SuccessStatus.GET_REVIEW_ACTION_PLAN_SUCCESS, reviewService.getReviewActionPlans(memberId, projectId));
    }

    @GetMapping("project/{projectId}/team/review")
    public ApiResponse<ReviewTeamStatusResponseDto> getTeamReviewStatus(@PathVariable Long projectId, @RequestParam String startDate,
                                                                        @RequestParam String endDate) {
        return ApiResponse.success(SuccessStatus.GET_REVIEW_TEAM_STATUS_SUCCESS, reviewService.getTeamReviewStatus(projectId, startDate, endDate));
    }

    @GetMapping("member/{memberId}/project/{projectId}/review")
    public ApiResponse<ReviewDetailResponseDto> getMyReviewDetail(@PathVariable Long memberId, @PathVariable Long projectId,
                                                                  @RequestParam String startDate, @RequestParam String endDate) {
        return ApiResponse.success(SuccessStatus.GET_MY_REVIEWS_DETAIL_SUCCESS, reviewService.getMyReviewDetail(memberId, projectId, startDate, endDate));
    }

    @GetMapping("member/{memberId}/project/{projectId}/reviews")
    public ApiResponse<MyReviewProjectResponseDto> getMyReviewProjects(@PathVariable Long memberId, @PathVariable Long projectId) {
        return ApiResponse.success(SuccessStatus.GET_PROJECT_MY_REVIEWS_SUCCESS, reviewService.getMyReviewProjects(memberId, projectId));
    }
}
