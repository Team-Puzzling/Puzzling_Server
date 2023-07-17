package com.puzzling.puzzlingServer.api.review.repository;

import com.puzzling.puzzlingServer.api.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMemberIdAndProjectId(Long memberId, Long projectId);

    @Query("SELECT r FROM Review r WHERE r.memberId = :memberId AND r.projectId = :projectId ORDER BY r.createdAt ASC")
    Page<Review> findTop15ByMemberIdAndProjectId(@Param("memberId") Long memberId, @Param("projectId") Long projectId, Pageable pageable);

    boolean existsReviewByReviewDate(String date);

    List<Review> findAllByProjectIdOrderByReviewDateAsc(Long projectId);

    List<Review> findAllByMemberIdAndProjectIdOrderByReviewDateAsc(Long memberId, Long projectId);
    List<Review> findAllByMemberIdAndProjectIdOrderByReviewDateDesc(Long memberId, Long projectId);
    Review findAllByMemberIdAndProjectId(Long memberId, Long projectId);
}
