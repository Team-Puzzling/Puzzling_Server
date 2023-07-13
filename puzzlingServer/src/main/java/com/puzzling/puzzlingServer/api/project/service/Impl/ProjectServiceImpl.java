package com.puzzling.puzzlingServer.api.project.service.Impl;

import com.puzzling.puzzlingServer.api.project.domain.UserProject;
import com.puzzling.puzzlingServer.api.project.dto.response.ProjectResponseDto;
import com.puzzling.puzzlingServer.api.project.repository.ProjectRepository;
import com.puzzling.puzzlingServer.api.project.domain.Project;
import com.puzzling.puzzlingServer.api.project.dto.response.ProjectVerifyResponseDto;
import com.puzzling.puzzlingServer.api.project.repository.UserProjectRepository;
import com.puzzling.puzzlingServer.api.project.service.ProjectService;
import com.puzzling.puzzlingServer.common.exception.NotFoundException;
import com.puzzling.puzzlingServer.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserProjectRepository userProjectRepository;

    @Override
    @Transactional
    public ProjectVerifyResponseDto verifyProjectCode(String invitationCode) {

        Project project = projectRepository.findByCode(invitationCode)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_PROJECT_CODE.getMessage()));

        return ProjectVerifyResponseDto.of(project.getId(), project.getName());
    }

    @Override
    @Transactional
    public List<ProjectResponseDto> getProjectAll(Long memberId) {
        List<UserProject> userProjects = userProjectRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId);
        return userProjects.stream()
                .flatMap(userProject -> projectRepository.findAllById(userProject.getProject().getId()).stream())
                .map(project -> ProjectResponseDto.of(project))
                .collect(Collectors.toList());
    }
}
