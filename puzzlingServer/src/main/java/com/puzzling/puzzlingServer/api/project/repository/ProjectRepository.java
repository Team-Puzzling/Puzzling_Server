package com.puzzling.puzzlingServer.api.project.repository;

import com.puzzling.puzzlingServer.api.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByCode(String invitationCode);
    List<Project> findAllById(Long projectId);

}
