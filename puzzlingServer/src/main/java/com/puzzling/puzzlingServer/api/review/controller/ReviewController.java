package com.puzzling.puzzlingServer.api.review.controller;

import com.puzzling.puzzlingServer.api.review.dto.response.ReviewTemplateGetResponseDto;
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
    public ApiResponse createReviewTIL(@PathVariable("memberId") Long memberId,@PathVariable("projectId") Long projectId, @Valid @RequestBody ReviewTILRequestDto reviewTILRequestDto){
        reviewService.createReviewTIL(memberId, projectId, reviewTILRequestDto);
        return ApiResponse.success(SuccessStatus.POST_REVIEW_SUCCESS.getStatusCode(), SuccessStatus.POST_REVIEW_SUCCESS.getMessage());
    }
}