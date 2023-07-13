package com.puzzling.puzzlingServer.api.member.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SocialInfoDto {
    private String id;
    private String nickname;
}