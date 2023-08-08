package com.puzzling.puzzlingServer.api.review.dto.response;

import com.puzzling.puzzlingServer.api.review.dto.response.objectDto.ReviewTeamStatusObjectDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ReviewTeamStatusResponseDto {
    private String projectName;
    private List<ReviewTeamStatusObjectDto> memberReviews;

    public static ReviewTeamStatusResponseDto of (String projectName, List<ReviewTeamStatusObjectDto> memberReviews) {
        return new ReviewTeamStatusResponseDto(projectName, memberReviews);
    }

}
