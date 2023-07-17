package com.puzzling.puzzlingServer.api.project.service;

import com.puzzling.puzzlingServer.api.project.dto.request.ProjectJoinRequestDto;
import com.puzzling.puzzlingServer.api.project.dto.request.ProjectRegisterRequestDto;
import com.puzzling.puzzlingServer.api.project.dto.response.*;

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

    //* 프로젝트 생성
    ProjectRegisterResponseDto createProject(Long memberId, ProjectRegisterRequestDto projectRegisterRequestDto);

    //* 프로젝트 참여
    ProjectJoinResponseDto joinProject(Long memberId, ProjectJoinRequestDto projectJoinRequestDto);

    //* 프로젝트 주기 조회
    ProjectCycleResponseDto getProjectCycle(Long projectId);
}
