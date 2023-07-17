package com.puzzling.puzzlingServer.api.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ProjectJoinRequestDto {

    private Long projectId;
    @NotBlank
    private String memberProjectNickname;
    @NotBlank
    private String memberProjectRole;
}
