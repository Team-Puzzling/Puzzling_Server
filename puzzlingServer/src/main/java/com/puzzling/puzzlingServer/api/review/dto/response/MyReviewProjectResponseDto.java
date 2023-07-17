package com.puzzling.puzzlingServer.api.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class MyReviewProjectResponseDto {
    private Long reviewId;
    private String reviewDate;
    private String contents;

    public static MyReviewProjectResponseDto of(Long reviewId, String reviewDate, String contents) {
        return new MyReviewProjectResponseDto(reviewId,reviewDate,contents);
    }
}
