package com.puzzling.puzzlingServer.api.project.repository;

import com.puzzling.puzzlingServer.api.project.domain.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProjectRepository extends JpaRepository<UserProject, Long> {

    List<UserProject> findAllByMemberIdOrderByCreatedAtDesc(Long memberId);

    List<UserProject> findByMemberIdOrderByCreatedAtDesc(Long memberId);

    List<UserProject> findAllByProjectId(Long projectId);
}
