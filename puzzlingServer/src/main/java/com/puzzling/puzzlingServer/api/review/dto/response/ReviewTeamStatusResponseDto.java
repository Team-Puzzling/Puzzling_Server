package com.puzzling.puzzlingServer.api.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ReviewTeamStatusResponseDto {
    private String reviewDay;
    private String reviewDate;
    private List<ReviewWriterObjectDto> reviewWriters;
    private List<ReviewWriterObjectDto> nonReviewWriters;

    public static ReviewTeamStatusResponseDto of (String reviewDay, String reviewDate, List<ReviewWriterObjectDto> reviewWriters,
                                            List<ReviewWriterObjectDto> nonReviewWriters) {
        return new ReviewTeamStatusResponseDto(reviewDay, reviewDate, reviewWriters, nonReviewWriters);
    }
}
