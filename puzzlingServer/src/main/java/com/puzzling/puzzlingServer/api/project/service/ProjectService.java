package com.puzzling.puzzlingServer.api.project.service;

import com.puzzling.puzzlingServer.api.project.dto.response.ProjectVerifyResponseDto;

public interface ProjectService {

    //* 프로젝트 초대코드 유효성 검사
    ProjectVerifyResponseDto verifyProjectCode(String invitationCode);
}
