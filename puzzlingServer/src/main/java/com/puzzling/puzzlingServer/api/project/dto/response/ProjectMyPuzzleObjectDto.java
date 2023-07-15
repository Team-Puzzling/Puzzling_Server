package com.puzzling.puzzlingServer.api.project.dto.response;

import com.puzzling.puzzlingServer.api.project.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ProjectMyPuzzleObjectDto {
    private String nickname;
    private int puzzleCount;

    public static ProjectMyPuzzleObjectDto of (String nickname, int puzzleCount) {
        return new ProjectMyPuzzleObjectDto(nickname, puzzleCount);
    }

}
