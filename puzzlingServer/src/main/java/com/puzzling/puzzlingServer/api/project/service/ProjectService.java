package com.puzzling.puzzlingServer.api.project.service;

import com.puzzling.puzzlingServer.api.project.domain.Project;
import com.puzzling.puzzlingServer.api.project.dto.response.ProjectOwnPuzzleResponseDto;
import com.puzzling.puzzlingServer.api.project.dto.response.ProjectResponseDto;
import com.puzzling.puzzlingServer.api.project.dto.response.ProjectTeamPuzzleResponseDto;
import com.puzzling.puzzlingServer.api.project.dto.response.ProjectVerifyResponseDto;

import java.security.Principal;
import java.util.List;

public interface ProjectService {

    //* 프로젝트 초대코드 유효성 검사
    ProjectVerifyResponseDto verifyProjectCode(String invitationCode);

    //* 참여 중인 프로젝트 리스트 조회
    List<ProjectResponseDto> getProjectAll(Long memberId);

    //* 개인 대시보드 퍼즐 조회
    ProjectOwnPuzzleResponseDto getMyPuzzles(Long memberId, Long projectId, String today);

    //* 팀 대시보드 퍼즐 조회
    ProjectTeamPuzzleResponseDto getTeamPuzzles(Principal principal, Long projectId, String today);
}
