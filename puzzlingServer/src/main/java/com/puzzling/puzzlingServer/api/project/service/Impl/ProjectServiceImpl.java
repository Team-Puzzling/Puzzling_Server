package com.puzzling.puzzlingServer.api.project.service.Impl;

import com.puzzling.puzzlingServer.api.project.ProjectRepository;
import com.puzzling.puzzlingServer.api.project.domain.Project;
import com.puzzling.puzzlingServer.api.project.dto.response.ProjectVerifyResponseDto;
import com.puzzling.puzzlingServer.api.project.service.ProjectService;
import com.puzzling.puzzlingServer.common.exception.BadRequestException;
import com.puzzling.puzzlingServer.common.exception.NotFoundException;
import com.puzzling.puzzlingServer.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public ProjectVerifyResponseDto verifyProjectCode(String invitationCode) {

        Project project = projectRepository.findByCode(invitationCode)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_PROJECT_CODE.getMessage()));

        return ProjectVerifyResponseDto.of(project.getId(), project.getName());
    }
}
