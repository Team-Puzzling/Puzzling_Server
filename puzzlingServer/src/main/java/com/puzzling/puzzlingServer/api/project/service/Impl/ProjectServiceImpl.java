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
import com.puzzling.puzzlingServer.common.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
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

        Pageable pageable = PageRequest.of(0, 15); // 첫 번째 페이지, 페이지 크기 15
        Page<Review> pageReviews = reviewRepository.findTop15ByMemberIdAndProjectId(memberId, projectId, pageable);
        List<Review> top15Reviews = pageReviews.getContent();

        Boolean isReviewDay = checkTodayIsReviewDay(today, projectId);
        Boolean hasTodayReview = reviewRepository.existsReviewByReviewDate(today);

        List<PuzzleObjectDto> result = new ArrayList<>();
        for (int idx = 1; idx <= top15Reviews.size(); idx++) {
            Review review = top15Reviews.get(idx - 1);
            result.add(PuzzleObjectDto.of(review.getReviewDate(), review.getId(), ("puzzleA" + idx)));
        }

        if (isReviewDay) {
            if (!hasTodayReview) {
                result.add(PuzzleObjectDto.of(null, null, "puzzleD" + result.size() + 1));
            }
        }
        return ProjectOwnPuzzleResponseDto.of(mapperMyPuzzleObject(memberId, projectId), result,
                isReviewDay, hasTodayReview);
    }

    @Override
    @Transactional
    public ProjectTeamPuzzleResponseDto getTeamPuzzles(Principal principal, Long projectId, String today) {
        if (today.equals("") || today == null) {
            throw new BadRequestException(ErrorStatus.VALIDATION_REQUEST_MISSING_EXCEPTION.getMessage());
        }
        Long memberId = MemberUtil.getMemberId(principal);
        Boolean isReviewDay = checkTodayIsReviewDay(today, projectId);
        Boolean hasTodayReview = reviewRepository.existsReviewByReviewDate(today);
        List<Review> reviews = reviewRepository.findAllByProjectIdOrderByReviewDateAsc(projectId);

        // 날짜별 리뷰 개수를 카운트하기 위한 Map 생성
        Map<String, Integer> reviewCountMap = new HashMap<>();

        // 날짜별 리뷰 개수 카운트 작업
        for (Review review : reviews) {
            String reviewDate = review.getReviewDate();
            reviewCountMap.put(reviewDate, reviewCountMap.getOrDefault(reviewDate, 0) + 1);
        }

        // reviewCountMap을 날짜 기준으로 최신순으로 정렬한 결과를 저장할 TreeMap 생성
        Map<String, Integer> sortedReviewCountMap = new TreeMap<>((date1, date2) -> date1.compareTo(date2));
        sortedReviewCountMap.putAll(reviewCountMap);

        // 결과 배열을 저장할 List 생성
        List<TeamPuzzleObjectDto> teamPuzzleBoard = new ArrayList<>();

        int idx = 1;
        // 날짜별 리뷰 개수를 배열 형태로 변환하여 저장
        for (Map.Entry<String, Integer> entry : sortedReviewCountMap.entrySet()) {
            String reviewMemberPercent = getReviewMemberPercent(projectId, entry.getValue());
            teamPuzzleBoard.add(TeamPuzzleObjectDto.of(entry.getKey(),
                    reviewMemberPercent, "puzzle"+reviewMemberPercent+idx++));
        }

        if (isReviewDay) {
            if (!hasTodayReview) {
                teamPuzzleBoard.add(TeamPuzzleObjectDto.of(null, null, "puzzleD" + (teamPuzzleBoard.size()+1)));
            }
        }
        return ProjectTeamPuzzleResponseDto.of(mapperMyPuzzleObject(memberId, projectId), teamPuzzleBoard,
                isReviewDay, hasTodayReview);
    }

    private String getReviewMemberPercent(Long projectId, int reviewCount) {
        int totalProjectMember = userProjectRepository.findAllByProjectId(projectId).size();
        float percent = (float) reviewCount/totalProjectMember;

        if (percent <= 0.334) {
            return "A";
        } else if (0.334 <= percent && percent <= 0.667 ) {
            return "B";
        } else {
            return "C";
        }
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

    private ProjectMyPuzzleObjectDto mapperMyPuzzleObject(Long memberId, Long projectId) {
        Member findMember = findMemberById(memberId);
        int puzzleCount = reviewRepository.findByMemberIdAndProjectId(memberId, projectId).size();
        return ProjectMyPuzzleObjectDto.of(findMember.getName(), puzzleCount);
    }

}
