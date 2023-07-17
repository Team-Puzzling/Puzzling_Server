package com.puzzling.puzzlingServer.api.project.controller;


import com.puzzling.puzzlingServer.api.project.dto.request.*;
import com.puzzling.puzzlingServer.api.project.dto.response.*;
import com.puzzling.puzzlingServer.api.project.service.ProjectService;
import com.puzzling.puzzlingServer.common.response.ApiResponse;
import com.puzzling.puzzlingServer.common.response.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("project/verify")
    public ApiResponse<ProjectVerifyResponseDto> verifyProjectCode(@RequestParam String invitationCode) {
        return ApiResponse.success(SuccessStatus.PROJECT_VERIFY_SUCCESS, projectService.verifyProjectCode(invitationCode));
    }

    @GetMapping("member/{memberId}/project/all")
    public ApiResponse<ProjectResponseDto> getProjectAll(@PathVariable Long memberId) {
        return ApiResponse.success(SuccessStatus.GET_PROJECT_ALL_SUCCESS, projectService.getProjectAll(memberId));
    }

    @GetMapping("member/{memberId}/project/{projectId}/puzzle")
    public ApiResponse<ProjectOwnPuzzleResponseDto> getMyPuzzles(@PathVariable Long memberId, @PathVariable Long projectId, @RequestParam String today) {
        return ApiResponse.success(SuccessStatus.GET_PROJECT_MY_PUZZLE_SUCCESS, projectService.getMyPuzzles(memberId, projectId, today));
    }

    @GetMapping("/project/{projectId}/team/puzzle")
    public ApiResponse<ProjectTeamPuzzleResponseDto> getTeamPuzzles(Principal principal, @PathVariable Long projectId, @RequestParam String today) {
        return ApiResponse.success(SuccessStatus.GET_PROJECT_TEAM_PUZZLE_SUCCESS, projectService.getTeamPuzzles(principal, projectId, today));
    }

    
    @PostMapping("member/{memberId}/project")
    public ApiResponse<ProjectRegisterResponseDto> createProject(@PathVariable("memberId") Long memberId, @Valid @RequestBody ProjectRegisterRequestDto projectRegisterRequestDto) {
        projectService.createProject(memberId, projectRegisterRequestDto);
        return ApiResponse.success(SuccessStatus.POST_PROJECT_SUCCESS.getStatusCode(), SuccessStatus.POST_PROJECT_SUCCESS.getMessage());
    }

    @GetMapping("project/{projectId}/rank")
    public ApiResponse<ProjectTeamRankResponseDto> getTeamRank(@PathVariable Long projectId) {
        return ApiResponse.success(SuccessStatus.GET_PROJECT_TEAM_RANK_SUCCESS, projectService.getTeamRank(projectId));
    }

    @PostMapping("member/{memberId}/project/join")
    public ApiResponse<ProjectJoinResponseDto>joinProject(@PathVariable("memberId") Long memberId, @Valid @RequestBody ProjectJoinRequestDto projectJoinRequestDto) {
        projectService.joinProject(memberId, projectJoinRequestDto);
        return ApiResponse.success(SuccessStatus.JOIN_PROJECT_SUCCESS.getStatusCode(), SuccessStatus.JOIN_PROJECT_SUCCESS.getMessage());
    }

    @GetMapping("project/{projectId}/cycle")
    public ApiResponse<ProjectCycleResponseDto>getProjectCycle(@PathVariable("projectId")Long projectId) {
        return ApiResponse.success(SuccessStatus.GET_PROJECT_CYCLE_SUCCESS, projectService.getProjectCycle(projectId));
    }
}
