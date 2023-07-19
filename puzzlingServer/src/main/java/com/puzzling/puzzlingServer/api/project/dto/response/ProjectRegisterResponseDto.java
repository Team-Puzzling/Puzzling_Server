package com.puzzling.puzzlingServer.api.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ProjectRegisterResponseDto {
    private String projectCode;
    private Long projectId;

    public static ProjectRegisterResponseDto of (String projectCode, Long projectId){
        return new ProjectRegisterResponseDto(projectCode,projectId);
    }
}
