package com.puzzling.puzzlingServer.api.project.service.Impl;

import com.puzzling.puzzlingServer.api.member.domain.Member;
import com.puzzling.puzzlingServer.api.member.repository.MemberRepository;
import com.puzzling.puzzlingServer.api.project.domain.UserProject;
import com.puzzling.puzzlingServer.api.project.dto.response.*;
import com.puzzling.puzzlingServer.api.project.repository.ProjectRepository;
import com.puzzling.puzzlingServer.api.project.domain.Project;
import com.puzzling.puzzlingServer.api.project.repository.UserProjectRepository;
import com.puzzling.puzzlingServer.api.project.service.ProjectService;
import com.puzzling.puzzlingServer.api.review.domain.Review;
import com.puzzling.puzzlingServer.api.review.repository.ReviewRepository;
import com.puzzling.puzzlingServer.common.exception.BadRequestException;
import com.puzzling.puzzlingServer.common.exception.NotFoundException;
import com.puzzling.puzzlingServer.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserProjectRepository userProjectRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public ProjectVerifyResponseDto verifyProjectCode(String invitationCode) {

        Project project = projectRepository.findByCode(invitationCode)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_PROJECT_CODE.getMessage()));

        return ProjectVerifyResponseDto.of(project.getId(), project.getName());
    }

    @Override
    @Transactional
    public List<ProjectResponseDto> getProjectAll(Long memberId) {
        List<UserProject> userProjects = userProjectRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId);
        return userProjects.stream()
                .flatMap(userProject -> projectRepository.findAllById(userProject.getProject().getId()).stream())
                .map(project -> ProjectResponseDto.of(project))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProjectOwnPuzzleResponseDto getMyPuzzles(Long memberId, Long projectId, String today) {
        if (today.equals("") || today == null) {
            throw new BadRequestException(ErrorStatus.VALIDATION_REQUEST_MISSING_EXCEPTION.getMessage());
        }
        Member findMember = findMemberById(memberId);
        int puzzleCount = reviewRepository.findByUserIdAndProjectId(memberId, projectId).size();
        ProjectMyPuzzleObjectDto projectMyPuzzleObjectDto = ProjectMyPuzzleObjectDto.of(findMember.getName(), puzzleCount);

        Pageable pageable = PageRequest.of(0, 15); // 첫 번째 페이지, 페이지 크기 15
        Page<Review> pageReviews = reviewRepository.findTop15ByMemberIdAndProjectId(memberId, projectId, pageable);
        List<Review> top15Reviews = pageReviews.getContent();

        Boolean isReviewDay = checkTodayIsReviewDay(today, projectId);
        Boolean hasTodayReview = reviewRepository.existsReviewByReviewDate(today);

        List<PuzzleObjectDto> result = new ArrayList<>();
        int idx = 1;
        for (Review review : top15Reviews) {
            result.add(PuzzleObjectDto.of(review.getReviewDate(), review.getId(), "puzzleA" + idx++));
        }

        if (isReviewDay) {
            if (!hasTodayReview) {
                result.add(PuzzleObjectDto.of(null, null, "puzzleD" + idx));
            }
        }
        return ProjectOwnPuzzleResponseDto.of(projectMyPuzzleObjectDto,result,isReviewDay,hasTodayReview);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BadRequestException(ErrorStatus.INVALID_MEMBER.getMessage()));
    }

    private Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_PROJECT.getMessage()));
    }

    private Boolean checkTodayIsReviewDay (String today, Long projectId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate todayDate = LocalDate.parse(today, formatter);

        DayOfWeek dayOfWeek = todayDate.getDayOfWeek();
        Locale koreanLocale = new Locale("ko", "KR");
        String dayOfWeekKorean = dayOfWeek.getDisplayName(TextStyle.SHORT, koreanLocale);

        String reviewCycle = findProjectById(projectId).getReviewCycle();
        List<String> weekdayList = Arrays.asList(reviewCycle.split(","));
        return weekdayList.contains(dayOfWeekKorean);
    }

}
