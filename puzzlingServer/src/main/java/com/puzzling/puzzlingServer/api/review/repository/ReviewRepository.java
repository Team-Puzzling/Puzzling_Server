package com.puzzling.puzzlingServer.api.review.repository;

import com.puzzling.puzzlingServer.api.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
