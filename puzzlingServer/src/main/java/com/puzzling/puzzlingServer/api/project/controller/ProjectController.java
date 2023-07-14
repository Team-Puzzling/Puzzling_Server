package com.puzzling.puzzlingServer.api.project.controller;

import com.puzzling.puzzlingServer.api.project.dto.response.ProjectOwnPuzzleResponseDto;
import com.puzzling.puzzlingServer.api.project.dto.response.ProjectResponseDto;
import com.puzzling.puzzlingServer.api.project.dto.response.ProjectVerifyResponseDto;
import com.puzzling.puzzlingServer.api.project.service.ProjectService;
import com.puzzling.puzzlingServer.common.response.ApiResponse;
import com.puzzling.puzzlingServer.common.response.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
