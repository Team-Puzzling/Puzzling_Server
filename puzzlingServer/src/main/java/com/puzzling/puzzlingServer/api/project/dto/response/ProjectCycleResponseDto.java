package com.puzzling.puzzlingServer.api.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCycleResponseDto {
    private String projectName;
    private String projectReviewCycle;

    public static ProjectCycleResponseDto of(String projectName, String projectReviewCycle){

        return new ProjectCycleResponseDto(projectName, projectReviewCycle);
    }
}
