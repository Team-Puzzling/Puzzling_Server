package com.puzzling.puzzlingServer.api.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ProjectTeamPuzzleResponseDto {
    private ProjectMyPuzzleObjectDto myPuzzle;
    private List<TeamPuzzleObjectDto> teamPuzzleBoard;
    private Boolean isReviewDay;
    private Boolean hasTodayReview;

    public static ProjectTeamPuzzleResponseDto of (ProjectMyPuzzleObjectDto projectMyPuzzleObjectDto, List<TeamPuzzleObjectDto> teamPuzzleBoard,
                                                   Boolean isReviewDay, Boolean hasTodayReview) {
        return new ProjectTeamPuzzleResponseDto (projectMyPuzzleObjectDto, teamPuzzleBoard, isReviewDay, hasTodayReview);
    }
}
