package com.puzzling.puzzlingServer.api.review.service.Impl;

import com.puzzling.puzzlingServer.api.project.domain.UserProject;
import com.puzzling.puzzlingServer.api.project.repository.UserProjectRepository;
import com.puzzling.puzzlingServer.api.review.domain.Review;
import com.puzzling.puzzlingServer.api.review.dto.request.Review5FRequestDto;
import com.puzzling.puzzlingServer.api.review.dto.response.ReviewActionPlanResponseDto;
import com.puzzling.puzzlingServer.api.review.dto.request.ReviewAARRequestDto;

import com.puzzling.puzzlingServer.api.review.dto.response.ReviewPreviousTemplateResponseDto;
import com.puzzling.puzzlingServer.api.review.dto.response.ReviewTemplateGetResponseDto;
import com.puzzling.puzzlingServer.api.review.dto.request.ReviewTILRequestDto;
import com.puzzling.puzzlingServer.api.review.repository.ReviewRepository;
import com.puzzling.puzzlingServer.api.review.service.ReviewService;
import com.puzzling.puzzlingServer.api.template.Repository.*;
import com.puzzling.puzzlingServer.api.template.domain.Review5F;
import com.puzzling.puzzlingServer.api.template.domain.ReviewAAR;
import com.puzzling.puzzlingServer.api.template.domain.ReviewTIL;
import com.puzzling.puzzlingServer.api.template.domain.ReviewTemplate;
import com.puzzling.puzzlingServer.common.exception.BadRequestException;
import com.puzzling.puzzlingServer.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewTemplateRepository reviewTemplateRepository;
    private final UserProjectRepository userProjectRepository;
    private final ReviewTILRepository reviewTILRepository;
    private final ReviewARRRepository reviewARRRepository;
    private final Review5FRepository review5FRepository;
    private final ReviewAARRepository reviewAARRepository;
    private final ReviewRepository reviewRepository;
    @Override
    @Transactional
    public List<ReviewTemplateGetResponseDto> getReviewTemplateAll() {
        List<ReviewTemplate> reviewTemplates = reviewTemplateRepository.findAll();
        return reviewTemplates.stream()
                .map(reviewTemplate -> ReviewTemplateGetResponseDto.of(reviewTemplate.getId(),reviewTemplate.getName(),reviewTemplate.getMeaning()))
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void createReviewTIL(Long memberId, Long projectId, ReviewTILRequestDto reviewTILRequestDto) {
        UserProject userProject = findUserProjectByMemberIdAndProjectId(memberId, projectId);

        if ( reviewTILRequestDto.getReviewTemplateId() == null ) {
            throw new BadRequestException("공백일 수 없습니다. (reviewTemplateId)");
        }
        ReviewTemplate reviewTemplate = findReviewTemplateById(reviewTILRequestDto.getReviewTemplateId());

        userProject.updatePreviousTemplateId(reviewTILRequestDto.getReviewTemplateId());

        Review review = Review.builder()
                .userProject(userProject)
                .reviewTemplate(reviewTemplate)
                .reviewDate("123")
                .memberId(memberId)
                .projectId(projectId)
                .build();
        Review savedReview = reviewRepository.save(review);

        ReviewTIL reviewTIL = ReviewTIL.builder()
                .review(savedReview)
                .liked(reviewTILRequestDto.getLiked())
                .lacked(reviewTILRequestDto.getLacked())
                .actionPlan(reviewTILRequestDto.getActionPlan())
                .build();
        reviewTILRepository.save(reviewTIL);
    }

    @Override
    @Transactional
    public void createReview5F(Long memberId, Long projectId, Review5FRequestDto review5FRequestDto) {
        UserProject userProject = findUserProjectByMemberIdAndProjectId(memberId, projectId);

        if ( review5FRequestDto.getReviewTemplateId() == null ) {
            throw new BadRequestException("공백일 수 없습니다. (reviewTemplateId)");
        }
        ReviewTemplate reviewTemplate = findReviewTemplateById(review5FRequestDto.getReviewTemplateId());

        userProject.updatePreviousTemplateId(review5FRequestDto.getReviewTemplateId());

        Review review = Review.builder()
                .userProject(userProject)
                .reviewTemplate(reviewTemplate)
                .reviewDate("123")
                .memberId(memberId)
                .projectId(projectId)
                .build();
        Review savedReview = reviewRepository.save(review);

        Review5F review5F = Review5F.builder()
                .review(savedReview)
                .fact(review5FRequestDto.getFact())
                .feeling(review5FRequestDto.getFeeling())
                .finding(review5FRequestDto.getFinding())
                .feedback(review5FRequestDto.getFeedback())
                .actionPlan(review5FRequestDto.getActionPlan())
                .build();
        review5FRepository.save(review5F);
    }

    @Override
    @Transactional
    public void createReviewAAR(Long memberId, Long projectId, ReviewAARRequestDto reviewAARRequestDto) {
        UserProject userProject = findUserProjectByMemberIdAndProjectId(memberId, projectId);

        if ( reviewAARRequestDto.getReviewTemplateId() == null ) {
            throw new BadRequestException("공백일 수 없습니다. (reviewTemplateId)");
        }
        ReviewTemplate reviewTemplate = findReviewTemplateById(reviewAARRequestDto.getReviewTemplateId());

        userProject.updatePreviousTemplateId(reviewAARRequestDto.getReviewTemplateId());

        Review review = Review.builder()
                .userProject(userProject)
                .reviewTemplate(reviewTemplate)
                .reviewDate("123")
                .memberId(memberId)
                .projectId(projectId)
                .build();
        Review savedReview = reviewRepository.save(review);

        ReviewAAR reviewAAR = ReviewAAR.builder()
                .review(savedReview)
                .initialGoal(reviewAARRequestDto.getInitialGoal())
                .result(reviewAARRequestDto.getResult())
                .difference(reviewAARRequestDto.getDifference())
                .persistence(reviewAARRequestDto.getPersistence())
                .actionPlan(reviewAARRequestDto.getActionPlan())
                .build();
        reviewARRRepository.save(reviewAAR);
    }

    public ReviewPreviousTemplateResponseDto getPreviousReviewTemplate(Long memberId, Long projectId) {
        UserProject findUserProject = findUserProjectByMemberIdAndProjectId(memberId, projectId);
        return ReviewPreviousTemplateResponseDto.of(findUserProject.getReviewTemplateId());
    }

    @Override
    @Transactional
    public List<ReviewActionPlanResponseDto> getReviewActionPlans(Long memberId, Long projectId) {
        List<Review> findReviews = reviewRepository.findAllByMemberIdAndProjectIdOrderByReviewDateAsc(memberId, projectId);

        if (findReviews.isEmpty()) {
            throw new BadRequestException("유저가 해당 프로젝트 팀원이 아닙니다.");
        }

        return findReviews.stream()
                .map(findReview -> {
                    String reviewTemplateName = findReview.getReviewTemplate().getName();

                    switch (reviewTemplateName) {
                        case "TIL":
                            ReviewTIL reviewTIL = findReviewByReviewId(findReview.getId(), reviewTILRepository, "TIL");
                            return ReviewActionPlanResponseDto.of(reviewTIL.getActionPlan(), findReview.getReviewDate());
                        case "5F":
                            Review5F review5F = findReviewByReviewId(findReview.getId(), review5FRepository, "5F");
                            return ReviewActionPlanResponseDto.of(review5F.getActionPlan(), findReview.getReviewDate());
                        case "AAR":
                            ReviewAAR reviewAAR = findReviewByReviewId(findReview.getId(), reviewAARRepository, "AAR");
                            return ReviewActionPlanResponseDto.of(reviewAAR.getActionPlan(), findReview.getReviewDate());
                        default:
                            throw new BadRequestException("올바르지 않은 리뷰 템플릿 이름: " + reviewTemplateName);
                    }
                })
                .collect(Collectors.toList());
    }

    private ReviewTemplate findReviewTemplateById (Long reviewTemplateId) {
        return reviewTemplateRepository.findById(reviewTemplateId)
                .orElseThrow(() -> new NotFoundException("해당하는 회고 팀플릿이 없습니다."));
    }

    private UserProject findUserProjectByMemberIdAndProjectId (Long memberId, Long projectId) {
        return userProjectRepository.findByMemberIdAndProjectId(memberId,projectId)
                .orElseThrow(() -> new NotFoundException("해당하는 멤버가 참여하는 프로젝트가 아닙니다."));
    }

    private <T> T findReviewByReviewId(Long reviewId, JpaRepository<T, Long> repository, String reviewTemplate) {
        try {
            Method findByReviewIdMethod = repository.getClass().getMethod("findByReviewId", Long.class);
            T result = (T) findByReviewIdMethod.invoke(repository, reviewId);

            if (result == null) {
                throw new NotFoundException(reviewTemplate + " 형식으로 작성한 회고를 찾을 수 없습니다.");
            }
            return result;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new NotFoundException(reviewTemplate + " 형식으로 작성한 회고를 찾을 수 없습니다.");
        }
    }
}
