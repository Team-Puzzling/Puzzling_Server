package com.puzzling.puzzlingServer.api.project.controller;

import com.puzzling.puzzlingServer.api.project.dto.response.ProjectVerifyResponseDto;
import com.puzzling.puzzlingServer.api.project.service.ProjectService;
import com.puzzling.puzzlingServer.common.response.ApiResponse;
import com.puzzling.puzzlingServer.common.response.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("verify")
    public ApiResponse<ProjectVerifyResponseDto> verifyProjectCode(@RequestParam String invitationCode) {
        return ApiResponse.success(SuccessStatus.PROJECT_VERIFY_SUCCESS, projectService.verifyProjectCode(invitationCode));
    }
}
