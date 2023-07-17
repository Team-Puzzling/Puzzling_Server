package com.puzzling.puzzlingServer.api.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class ReviewWriterObjectDto {
    private String memberNickname;
    private String memberRole;

    public static ReviewWriterObjectDto of (String memberNickname, String memberRole) {
        return new ReviewWriterObjectDto(memberNickname, memberRole);
    }
}
