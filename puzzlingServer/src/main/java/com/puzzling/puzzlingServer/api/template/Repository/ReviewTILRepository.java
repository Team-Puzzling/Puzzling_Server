package com.puzzling.puzzlingServer.api.template.Repository;

import com.puzzling.puzzlingServer.api.template.domain.ReviewTIL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewTILRepository extends JpaRepository<ReviewTIL, Long> {
    ReviewTIL findByReviewId(Long reviewId);
}
