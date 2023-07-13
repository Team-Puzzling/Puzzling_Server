package com.puzzling.puzzlingServer.api.project.dto.response;

import com.puzzling.puzzlingServer.api.project.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ProjectResponseDto {

    private Long projectId;
    private String projectStartDate;
    private String projectName;

    public static ProjectResponseDto of (Project project) {
        return new ProjectResponseDto(project.getId(), project.getStartDate(), project.getName());
    }
}
