package com.puzzling.puzzlingServer.api.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class PuzzleObjectDto {
    private String reviewDate;
    private Long reviewId;
    private String puzzleAssetName;

    public static PuzzleObjectDto of(String reviewDate, Long reviewId, String puzzleAssetName) {
        return new PuzzleObjectDto(reviewDate, reviewId, puzzleAssetName);
    }
}
