package com.puzzling.puzzlingServer.api.template.Repository;

import com.puzzling.puzzlingServer.api.template.domain.ReviewTIL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ReviewTILRepository extends JpaRepository<ReviewTIL, Long> {

}
