package com.puzzling.puzzlingServer.api.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ReviewContentObjectDto {
    private String title;
    private String content;

    public static ReviewContentObjectDto of (String title, String content) {
        return new ReviewContentObjectDto(title, content);
    }
}
