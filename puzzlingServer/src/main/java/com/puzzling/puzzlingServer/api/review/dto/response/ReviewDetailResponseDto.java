package com.puzzling.puzzlingServer.api.review.dto.response;

import com.puzzling.puzzlingServer.api.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ReviewDetailResponseDto {
    private Long reviewId;
    private String reviewDay;
    private String reviewDate;
    private Long reviewTemplateId;
    private List<ReviewContentObjectDto> contents;

    public static ReviewDetailResponseDto of (Long reviewId, String reviewDay, String reviewDate,
                                                Long reviewTemplateId, List<ReviewContentObjectDto> contents) {
        return new ReviewDetailResponseDto(reviewId, reviewDay, reviewDate, reviewTemplateId, contents);
    }
}
