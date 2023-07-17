package com.puzzling.puzzlingServer.api.project.dto.response;

import com.puzzling.puzzlingServer.api.project.domain.UserProject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ProjectTeamRankResponseDto {
    private int memberRank;
    private String memberNickname;
    private String memberRole;
    private int memberPuzzleCount;

    public static ProjectTeamRankResponseDto of (int memberRank, UserProject userProject) {
        return new ProjectTeamRankResponseDto(memberRank, userProject.getNickname(), userProject.getRole(), userProject.getReviewCount());
    }

}
