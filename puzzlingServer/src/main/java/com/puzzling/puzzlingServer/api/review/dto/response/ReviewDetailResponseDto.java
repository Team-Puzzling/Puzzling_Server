package com.puzzling.puzzlingServer.api.review.dto.response;

import com.puzzling.puzzlingServer.api.review.dto.response.objectDto.ReviewDetailObjectDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ReviewDetailResponseDto {
    private String projectName;
    private List<ReviewDetailObjectDto> reviews;

    public static ReviewDetailResponseDto of (String projectName, List<ReviewDetailObjectDto> reviews) {
        return new ReviewDetailResponseDto(projectName, reviews);
    }
}
