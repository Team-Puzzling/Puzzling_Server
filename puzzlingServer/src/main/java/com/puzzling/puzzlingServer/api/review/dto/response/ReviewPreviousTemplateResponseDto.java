package com.puzzling.puzzlingServer.api.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ReviewPreviousTemplateResponseDto {
    private Long previousTemplateId;

    public static ReviewPreviousTemplateResponseDto of (Long previousTemplateId) {
        return new ReviewPreviousTemplateResponseDto(previousTemplateId);
    }
}
