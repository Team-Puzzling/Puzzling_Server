package com.puzzling.puzzlingServer.api.review.repository;

import com.puzzling.puzzlingServer.api.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserIdAndProjectId(Long memberId, Long projectId);

    @Query("SELECT r FROM Review r WHERE r.userId = :memberId AND r.projectId = :projectId ORDER BY r.createdAt ASC")
    Page<Review> findTop15ByMemberIdAndProjectId(Long memberId, Long projectId, Pageable pageable);

    boolean existsReviewByReviewDate(String date);
}
