package com.puzzling.puzzlingServer.api.review.controller;

import com.puzzling.puzzlingServer.api.review.dto.ReviewTemplateGetResponseDto;
import com.puzzling.puzzlingServer.api.review.service.ReviewService;
import com.puzzling.puzzlingServer.common.response.ApiResponse;
import com.puzzling.puzzlingServer.common.response.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("template")
    public ApiResponse<ReviewTemplateGetResponseDto> getReviewTemplateAll() {
        return ApiResponse.success(SuccessStatus.GET_REVIEW_TEMPLATE_SUCCESS,reviewService.getReviewTemplateAll());
    }
}
