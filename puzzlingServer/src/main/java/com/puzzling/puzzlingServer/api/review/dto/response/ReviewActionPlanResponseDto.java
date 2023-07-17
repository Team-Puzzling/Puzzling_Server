package com.puzzling.puzzlingServer.api.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ReviewActionPlanResponseDto {
    private String actionPlanContent;
    private String actionPlanDate;

    public static ReviewActionPlanResponseDto of (String actionPlanContent, String actionPlanDate) {
        return new ReviewActionPlanResponseDto(actionPlanContent, actionPlanDate);
    }
}
