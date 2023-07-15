package com.puzzling.puzzlingServer.api.review.dto;

import com.puzzling.puzzlingServer.api.template.domain.ReviewTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ReviewTemplateGetResponseDto {

    private Long reviewTemplateId;
    private String reviewTemplateName;
    private String reviewTemplateMeaning;

    public static ReviewTemplateGetResponseDto of(Long reviewTemplateId,String reviewTemplateName,String reviewTemplateMeaning){
        return new ReviewTemplateGetResponseDto(reviewTemplateId,reviewTemplateName,reviewTemplateMeaning);
    }
}
