package com.puzzling.puzzlingServer.api.project.service.Impl;

import com.puzzling.puzzlingServer.api.project.ProjectRepository;
import com.puzzling.puzzlingServer.api.project.domain.Project;
import com.puzzling.puzzlingServer.api.project.dto.response.ProjectVerifyResponseDto;
import com.puzzling.puzzlingServer.api.project.service.ProjectService;
import com.puzzling.puzzlingServer.common.exception.BadRequestException;
import com.puzzling.puzzlingServer.common.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
class ProjectServiceImplTest {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectService projectService;

    @DisplayName("프로젝트 초대코드 유효성 검사")
    @Test
    void 성공_VerifyProjectCode() {

        // given
        Project project = Project.builder()
                .code("ABC1234")
                .createUserId(1L)
                .intro("테스트 프로젝트입니다.")
                .name("테스트 프로젝트")
                .reviewCycle("월,수")
                .startDate("2023-07-23")
                .build();
        projectRepository.save(project);

        // when
        ProjectVerifyResponseDto responseDto = projectService.verifyProjectCode("ABC1234");

        // then
        assertThat(responseDto.getProjectName()).isEqualTo(project.getName());
        assertThat(responseDto.getProjectId()).isEqualTo(project.getId());
    }

    @DisplayName("프로젝트 초대코드 유효성 검사 - 실패")
    @Test
     void 실패_VerifyProjectCode() throws NotFoundException {

        // given
        Project project = Project.builder()
                .code("ABC1234")
                .createUserId(1L)
                .intro("테스트 프로젝트입니다.")
                .name("테스트 프로젝트")
                .reviewCycle("월,수")
                .startDate("2023-07-23")
                .build();
        projectRepository.save(project);

        // when, then
        assertThrows(NotFoundException.class, () -> {
            projectService.verifyProjectCode("INVALID_CODE");
        });
    }
}