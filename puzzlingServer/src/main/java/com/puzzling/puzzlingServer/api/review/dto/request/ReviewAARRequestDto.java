package com.puzzling.puzzlingServer.api.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ReviewAARRequestDto {

    private Long reviewTemplateId;
    @NotBlank
    private String initialGoal;
    @NotBlank
    private String result;
    @NotBlank
    private String difference;
    @NotBlank
    private String persistence;
    @NotBlank
    private String actionPlan;

}