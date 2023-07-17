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

    public static ProjectJoinResponseDto of(Long projectId){
        return new ProjectJoinResponseDto(projectId);
    }
}
