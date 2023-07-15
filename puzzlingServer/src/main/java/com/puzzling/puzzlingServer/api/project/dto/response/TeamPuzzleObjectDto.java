package com.puzzling.puzzlingServer.api.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class TeamPuzzleObjectDto {
    private String reviewDate;
    private String reviewMemberPercent;
    private String puzzleAssetName;

    public static TeamPuzzleObjectDto of(String reviewDate, String reviewMemberPercent, String puzzleAssetName) {
        return new TeamPuzzleObjectDto(reviewDate, reviewMemberPercent, puzzleAssetName);
    }
}
