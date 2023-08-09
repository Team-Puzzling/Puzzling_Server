package com.puzzling.puzzlingServer.api.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ProjectJoinResponseDto {
    private Long projectId;
    private String projectName;

    public static ProjectJoinResponseDto of(Long projectId, String projectName){
        return new ProjectJoinResponseDto(projectId, projectName);
    }
}
