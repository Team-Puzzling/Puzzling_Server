package com.puzzling.puzzlingServer.api.template.Repository;

import com.puzzling.puzzlingServer.api.template.domain.Review5F;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Review5FRepository extends JpaRepository<Review5F, Long> {
    Review5F findByReviewId(Long reviewId);
}
