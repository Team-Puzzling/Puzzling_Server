package com.puzzling.puzzlingServer.api.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ReviewPostResponseDto {
    private String projectName;

    public static ReviewPostResponseDto of (String projectName) {
        return new ReviewPostResponseDto(projectName);
    }
}
