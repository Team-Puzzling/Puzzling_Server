package com.puzzling.puzzlingServer.api.review.service.Impl;

import com.puzzling.puzzlingServer.api.project.domain.UserProject;
import com.puzzling.puzzlingServer.api.project.repository.UserProjectRepository;
import com.puzzling.puzzlingServer.api.review.domain.Review;
import com.puzzling.puzzlingServer.api.review.dto.request.Review5FRequestDto;
import com.puzzling.puzzlingServer.api.review.dto.response.ReviewTemplateGetResponseDto;
import com.puzzling.puzzlingServer.api.review.dto.request.ReviewTILRequestDto;
import com.puzzling.puzzlingServer.api.review.repository.ReviewRepository;
import com.puzzling.puzzlingServer.api.review.service.ReviewService;
import com.puzzling.puzzlingServer.api.template.Repository.Review5FRepository;
import com.puzzling.puzzlingServer.api.template.Repository.ReviewTILRepository;
import com.puzzling.puzzlingServer.api.template.Repository.ReviewTemplateRepository;
import com.puzzling.puzzlingServer.api.template.domain.Review5F;
import com.puzzling.puzzlingServer.api.template.domain.ReviewTIL;
import com.puzzling.puzzlingServer.api.template.domain.ReviewTemplate;
import com.puzzling.puzzlingServer.common.exception.BadRequestException;
import com.puzzling.puzzlingServer.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewTemplateRepository reviewTemplateRepository;
    private final UserProjectRepository userProjectRepository;
    private final ReviewTILRepository reviewTILRepository;
    private final Review5FRepository review5FRepository;
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
        UserProject userProject = userProjectRepository.findByMemberIdAndProjectId(memberId,projectId);

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
        UserProject userProject = userProjectRepository.findByMemberIdAndProjectId(memberId,projectId);

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


    private ReviewTemplate findReviewTemplateById (Long reviewTemplateId) {
        return reviewTemplateRepository.findById(reviewTemplateId)
                .orElseThrow(() -> new NotFoundException("해당하는 회고 팀플릿이 없습니다"));
    }
}
