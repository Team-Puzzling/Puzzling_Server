package com.puzzling.puzzlingServer.api.project.repository;

import com.puzzling.puzzlingServer.api.project.domain.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProjectRepository extends JpaRepository<UserProject, Long> {
<<<<<<< Updated upstream
    List<UserProject> findAllByMemberIdOrderByCreatedAtDesc(Long memberId);
}
=======

    Long getMostRecentProjectIdByMemberId(Long memberId);

    List<UserProject> findByMemberIdOrderByCreatedAtDesc(Long memberId);


}
>>>>>>> Stashed changes
