package com.puzzling.puzzlingServer.api.review.service.Impl;

import com.puzzling.puzzlingServer.api.member.domain.Member;
import com.puzzling.puzzlingServer.api.member.repository.MemberRepository;
import com.puzzling.puzzlingServer.api.project.domain.Project;
import com.puzzling.puzzlingServer.api.project.domain.UserProject;
import com.puzzling.puzzlingServer.api.project.repository.ProjectRepository;
import com.puzzling.puzzlingServer.api.project.repository.UserProjectRepository;
import com.puzzling.puzzlingServer.api.review.domain.Review;
import com.puzzling.puzzlingServer.api.review.dto.request.Review5FRequestDto;
import com.puzzling.puzzlingServer.api.review.dto.response.*;

import com.puzzling.puzzlingServer.api.review.dto.response.MyReviewProjectResponseDto;
import com.puzzling.puzzlingServer.api.review.dto.response.ReviewActionPlanResponseDto;

import com.puzzling.puzzlingServer.api.review.dto.request.ReviewAARRequestDto;

import com.puzzling.puzzlingServer.api.review.dto.request.ReviewTILRequestDto;
import com.puzzling.puzzlingServer.api.review.dto.response.objectDto.ReviewContentObjectDto;
import com.puzzling.puzzlingServer.api.review.dto.response.objectDto.ReviewDetailObjectDto;
import com.puzzling.puzzlingServer.api.review.dto.response.objectDto.ReviewTeamStatusObjectDto;
import com.puzzling.puzzlingServer.api.review.repository.ReviewRepository;
import com.puzzling.puzzlingServer.api.review.service.ReviewService;

import com.puzzling.puzzlingServer.api.template.Repository.Review5FRepository;
import com.puzzling.puzzlingServer.api.template.Repository.ReviewAARRepository;
import com.puzzling.puzzlingServer.api.template.Repository.ReviewTILRepository;
import com.puzzling.puzzlingServer.api.template.Repository.ReviewTemplateRepository;


import com.puzzling.puzzlingServer.api.template.domain.Review5F;
import com.puzzling.puzzlingServer.api.template.domain.ReviewAAR;
import com.puzzling.puzzlingServer.api.template.domain.ReviewTIL;
import com.puzzling.puzzlingServer.api.template.domain.ReviewTemplate;
import com.puzzling.puzzlingServer.api.template.strategy.AARReviewTemplateStrategy;
import com.puzzling.puzzlingServer.api.template.strategy.FiveFReviewTemplateStrategy;
import com.puzzling.puzzlingServer.api.template.strategy.TILReviewTemplateStrategy;
import com.puzzling.puzzlingServer.common.exception.BadRequestException;
import com.puzzling.puzzlingServer.common.exception.NotFoundException;
import com.puzzling.puzzlingServer.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.puzzling.puzzlingServer.common.response.ErrorStatus.NOT_FOUND_PROJECT;
import static com.puzzling.puzzlingServer.common.util.DateUtil.*;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewTemplateRepository reviewTemplateRepository;
    private final UserProjectRepository userProjectRepository;
    private final ReviewTILRepository reviewTILRepository;
    private final ReviewAARRepository reviewARRRepository;
    private final Review5FRepository review5FRepository;
    private final ReviewAARRepository reviewAARRepository;
    private final ReviewRepository reviewRepository;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

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
    public ReviewPostResponseDto createReviewTIL(Long memberId, Long projectId, ReviewTILRequestDto reviewTILRequestDto) {
        UserProject userProject = findUserProjectByMemberIdAndProjectId(memberId, projectId);

        if ( reviewTILRequestDto.getReviewTemplateId() == null ) {
            throw new BadRequestException("공백일 수 없습니다. (reviewTemplateId)");
        }
        ReviewTemplate reviewTemplate = findReviewTemplateById(reviewTILRequestDto.getReviewTemplateId());

        userProject.updatePreviousTemplateId(reviewTILRequestDto.getReviewTemplateId());
        userProject.addReviewCount();

        Review review = Review.builder()
                .userProject(userProject)
                .reviewTemplate(reviewTemplate)
                .reviewDate(convertLocalDatetoString(LocalDate.now()))
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
        return ReviewPostResponseDto.of(findProjectById(projectId).getName());
    }

    @Override
    @Transactional
    public ReviewPostResponseDto createReview5F(Long memberId, Long projectId, Review5FRequestDto review5FRequestDto) {
        UserProject userProject = findUserProjectByMemberIdAndProjectId(memberId, projectId);

        if ( review5FRequestDto.getReviewTemplateId() == null ) {
            throw new BadRequestException("공백일 수 없습니다. (reviewTemplateId)");
        }
        ReviewTemplate reviewTemplate = findReviewTemplateById(review5FRequestDto.getReviewTemplateId());

        userProject.updatePreviousTemplateId(review5FRequestDto.getReviewTemplateId());
        userProject.addReviewCount();

        Review review = Review.builder()
                .userProject(userProject)
                .reviewTemplate(reviewTemplate)
                .reviewDate(convertLocalDatetoString(LocalDate.now()))
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
        return ReviewPostResponseDto.of(findProjectById(projectId).getName());
    }

    @Override
    @Transactional
    public ReviewPostResponseDto createReviewAAR(Long memberId, Long projectId, ReviewAARRequestDto reviewAARRequestDto) {
        UserProject userProject = findUserProjectByMemberIdAndProjectId(memberId, projectId);

        if ( reviewAARRequestDto.getReviewTemplateId() == null ) {
            throw new BadRequestException("공백일 수 없습니다. (reviewTemplateId)");
        }
        ReviewTemplate reviewTemplate = findReviewTemplateById(reviewAARRequestDto.getReviewTemplateId());

        userProject.updatePreviousTemplateId(reviewAARRequestDto.getReviewTemplateId());
        userProject.addReviewCount();

        Review review = Review.builder()
                .userProject(userProject)
                .reviewTemplate(reviewTemplate)
                .reviewDate(convertLocalDatetoString(LocalDate.now()))
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
        return ReviewPostResponseDto.of(findProjectById(projectId).getName());
    }

    @Override
    @Transactional
    public ReviewPreviousTemplateResponseDto getPreviousReviewTemplate(Long memberId, Long projectId) {
        UserProject findUserProject = findUserProjectByMemberIdAndProjectId(memberId, projectId);
        return ReviewPreviousTemplateResponseDto.of(findUserProject.getReviewTemplateId());
    }

    @Override
    @Transactional
    public List<ReviewActionPlanResponseDto> getReviewActionPlans(Long memberId, Long projectId) {

        findUserProjectByMemberIdAndProjectId(memberId, projectId);

        List<Review> findReviews = reviewRepository.findAllByMemberIdAndProjectIdOrderByReviewDateDesc(memberId, projectId);

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

    @Override
    @Transactional
    public ReviewTeamStatusResponseDto getTeamReviewStatus(Long projectId, String startDate, String endDate) {

        List<ReviewTeamStatusObjectDto> result = new ArrayList<>();

        List<UserProject> userProjects = userProjectRepository.findAllByProjectId(projectId);
        Project project = findProjectById(projectId);
        String reviewCycle = project.getReviewCycle();

        List<String> reviewDates = generateReviewDates(startDate, endDate, reviewCycle);

        for (String reviewDate : reviewDates) {
            List<ReviewWriterObjectDto> reviewWriters = new ArrayList<>();
            List<ReviewWriterObjectDto> nonReviewWriters = new ArrayList<>();

            for (UserProject userProject : userProjects) {
                Long memberId = userProject.getMember().getId();
                Optional<Review> findReview = reviewRepository.findByMemberIdAndProjectIdAndReviewDate(memberId, projectId, reviewDate);

                if (findReview.isPresent()) {
                    reviewWriters.add(ReviewWriterObjectDto.of(userProject.getNickname(), userProject.getRole()));
                } else {
                    nonReviewWriters.add(ReviewWriterObjectDto.of(userProject.getNickname(), userProject.getRole()));
                }
            }

            result.add(ReviewTeamStatusObjectDto.of(getDayOfWeek(reviewDate), reviewDate, reviewWriters.isEmpty() ? null : reviewWriters, nonReviewWriters.isEmpty() ? null : nonReviewWriters));

        }
        return ReviewTeamStatusResponseDto.of(project.getName(), result);
    }

    @Override
    @Transactional
    public ReviewDetailResponseDto getMyReviewDetail(Long memberId, Long projectId, String startDate, String endDate) {
        List<ReviewDetailObjectDto> result = new ArrayList<>();
        findMemberById(memberId);
        Project project = findProjectById(projectId);
        String reviewCycle = project.getReviewCycle();

        List<String> reviewDates = generateReviewDates(startDate, endDate, reviewCycle);

        for (String reviewDate : reviewDates) {
            Optional<Review> findReview = reviewRepository.findByMemberIdAndProjectIdAndReviewDate(memberId, projectId, reviewDate);
            List<ReviewContentObjectDto> reviewContent = new ArrayList<>();

            if (findReview.isPresent()) {
                Review reviewValue = findReview.get();
                String reviewTemplateName = reviewValue.getReviewTemplate().getName();

                switch (reviewTemplateName) {
                    case "TIL":
                        ReviewTIL reviewTIL = findReviewByReviewId(reviewValue.getId(), reviewTILRepository, "TIL");
                        TILReviewTemplateStrategy TILStrategy = new TILReviewTemplateStrategy();
                        TILStrategy.getReviewContents(reviewTIL, reviewContent);
                        break;
                    case "5F":
                        Review5F review5F = findReviewByReviewId(reviewValue.getId(), review5FRepository, "5F");
                        FiveFReviewTemplateStrategy FiveFStrategy = new FiveFReviewTemplateStrategy();
                        FiveFStrategy.getReviewContents(review5F, reviewContent);
                        break;
                    case "AAR":
                        ReviewAAR reviewAAR = findReviewByReviewId(reviewValue.getId(), reviewAARRepository, "AAR");
                        AARReviewTemplateStrategy AARStrategy = new AARReviewTemplateStrategy();
                        AARStrategy.getReviewContents(reviewAAR, reviewContent);
                        break;
                    default:
                        throw new BadRequestException("올바르지 않은 리뷰 템플릿 이름: " + reviewTemplateName);
                }

                result.add(ReviewDetailObjectDto.of(reviewValue.getId(), getDayOfWeek(reviewDate), reviewDate,
                        reviewValue.getReviewTemplate().getId(), reviewContent));
            } else {
                result.add(ReviewDetailObjectDto.of(null, getDayOfWeek(reviewDate), reviewDate,
                        null, null));
            }
        }
        return ReviewDetailResponseDto.of(project.getName(), result);
    }


    @Override
    @Transactional
    public List<MyReviewProjectResponseDto> getMyReviewProjects(Long memberId, Long projectId) {
        List<Review> findReviews = reviewRepository.findAllByMemberIdAndProjectIdOrderByReviewDateDesc(memberId, projectId);

        return findReviews.stream()
                .map(findReview -> {
                    String reviewTemplateName = findReview.getReviewTemplate().getName();

                    switch (reviewTemplateName) {
                        case "TIL":
                            ReviewTIL reviewTIL = findReviewByReviewId(findReview.getId(), reviewTILRepository, "TIL");
                            return MyReviewProjectResponseDto.of(findReview.getId(), findReview.getReviewDate(), reviewTIL.getActionPlan());
                        case "5F":
                            Review5F review5F = findReviewByReviewId(findReview.getId(), review5FRepository, "5F");
                            return MyReviewProjectResponseDto.of(findReview.getId(), findReview.getReviewDate(), review5F.getActionPlan());
                        case "AAR":
                            ReviewAAR reviewAAR = findReviewByReviewId(findReview.getId(), reviewAARRepository, "AAR");
                            return MyReviewProjectResponseDto.of(findReview.getId(), findReview.getReviewDate(), reviewAAR.getActionPlan());
                        default:
                            throw new BadRequestException("올바르지 않은 리뷰 템플릿 이름: " + reviewTemplateName);
                    }
                })
                .collect(Collectors.toList());
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_MEMBER.getMessage()));
    }

    private Project findProjectById (Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(() ->
                new NotFoundException(NOT_FOUND_PROJECT.getMessage()));
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

    private List<String> generateReviewDates(String startDate, String endDate, String reviewCycle) {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        List<String> reviewDates = new ArrayList<>();

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (checkTodayIsReviewDay( date.format(DateTimeFormatter.ISO_DATE), reviewCycle)) {
                reviewDates.add(date.format(DateTimeFormatter.ISO_DATE));
            }
        }
        return reviewDates;
    }

}
