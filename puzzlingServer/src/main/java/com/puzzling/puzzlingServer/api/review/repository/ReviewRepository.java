package com.puzzling.puzzlingServer.api.review.repository;

import com.puzzling.puzzlingServer.api.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMemberIdAndProjectId(Long memberId, Long projectId);

    @Query("SELECT r FROM Review r WHERE r.memberId = :memberId AND r.projectId = :projectId ORDER BY r.createdAt")
    Page<Review> findByMemberIdAndProjectIdOrderByCreatedAt(@Param("memberId") Long memberId, @Param("projectId") Long projectId, Pageable pageable);

    boolean existsReviewByReviewDate(String date);

    List<Review> findAllByProjectIdOrderByReviewDateAsc(Long projectId);

    List<Review> findAllByMemberIdAndProjectIdOrderByReviewDateDesc(Long memberId, Long projectId);

    Optional<Review> findByMemberIdAndProjectIdAndReviewDate(Long memberId, Long projectId, String ReviewDate);
}
