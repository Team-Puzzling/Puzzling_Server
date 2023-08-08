package com.puzzling.puzzlingServer.api.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ProjectOwnPuzzleResponseDto {
    private String projectName;
    private ProjectMyPuzzleObjectDto myPuzzle;
    private List<PuzzleObjectDto> userPuzzleBoard;
    private int puzzleBoardCount;
    private Boolean isReviewDay;
    private Boolean hasTodayReview;

    public static ProjectOwnPuzzleResponseDto of(String projectName, ProjectMyPuzzleObjectDto projectMyPuzzleObjectDto, List<PuzzleObjectDto> userPuzzleBoard,
                                                 int puzzleBoardCount, Boolean isReviewDay, Boolean hasTodayReview) {
        return new ProjectOwnPuzzleResponseDto(projectName, projectMyPuzzleObjectDto, userPuzzleBoard, puzzleBoardCount, isReviewDay, hasTodayReview);
    }

}
