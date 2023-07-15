package com.puzzling.puzzlingServer.api.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ReviewTILRequestDto {

    private Long reviewTemplateId;
    @NotBlank
    private String liked;
    @NotBlank
    private String lacked;
    @NotBlank
    private String actionPlan;

}
