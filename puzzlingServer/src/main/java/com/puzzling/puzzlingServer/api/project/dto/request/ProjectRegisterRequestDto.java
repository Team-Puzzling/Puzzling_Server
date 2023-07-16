package com.puzzling.puzzlingServer.api.project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import java.lang.reflect.Array;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ProjectRegisterRequestDto {
    @NotBlank
    private String projectName;
    @NotBlank
    private String projectIntro;
    @NotBlank
    private String projectStartDate;
    @NotBlank
    private String memberProjectRole;
    @NotBlank
    private String memberProjectNickname;
    @NotEmpty
    private String[] reviewCycle;
}
