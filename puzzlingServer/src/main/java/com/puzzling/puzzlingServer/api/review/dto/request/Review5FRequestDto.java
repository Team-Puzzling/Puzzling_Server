package com.puzzling.puzzlingServer.api.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Review5FRequestDto {

    private Long reviewTemplateId;
    @NotBlank
    private String fact;
    @NotBlank
    private String feeling;
    @NotBlank
    private String finding;
    @NotBlank
    private String feedback;
    @NotBlank
    private String actionPlan;

}