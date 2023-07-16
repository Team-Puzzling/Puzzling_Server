package com.puzzling.puzzlingServer.api.project.repository;

import com.puzzling.puzzlingServer.api.project.domain.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProjectRepository extends JpaRepository<UserProject, Long> {

    List<UserProject> findAllByMemberIdOrderByCreatedAtDesc(Long memberId);

    List<UserProject> findByMemberIdOrderByCreatedAtDesc(Long memberId);

    List<UserProject> findAllByProjectId(Long projectId);

    Optional<UserProject> findByMemberIdAndProjectId(Long memberId, Long projectId);

}
