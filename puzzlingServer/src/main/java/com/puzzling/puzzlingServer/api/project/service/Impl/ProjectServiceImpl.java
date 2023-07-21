package com.puzzling.puzzlingServer.api.project.service.Impl;

import com.puzzling.puzzlingServer.api.member.domain.Member;
import com.puzzling.puzzlingServer.api.member.repository.MemberRepository;
import com.puzzling.puzzlingServer.api.project.domain.UserProject;
import com.puzzling.puzzlingServer.api.project.dto.request.ProjectJoinRequestDto;
import com.puzzling.puzzlingServer.api.project.dto.request.ProjectRegisterRequestDto;
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

import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static com.puzzling.puzzlingServer.common.util.DateUtil.checkTodayIsReviewDay;

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

        if (userProjects.isEmpty()) {
            throw new NotFoundException(ErrorStatus.NOT_FOUND_USER_PROJECT.getMessage());
        }

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

        if (findMemberById(memberId) == null) {
            throw new BadRequestException(ErrorStatus.NOT_FOUND_MEMBER.getMessage());
        }

        int pageSize = 15;
        int pageNumber = 0; // 첫 번째 페이지
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Review> pageReviews = reviewRepository.findByMemberIdAndProjectIdOrderByCreatedAt(memberId, projectId, pageable);
        List<Review> reviews = new ArrayList<>();

        if (pageReviews.getTotalPages() > 0) {
            int lastPageNumber = pageReviews.getTotalPages() - 1;
            pageable = PageRequest.of(lastPageNumber, pageSize);
            pageReviews = reviewRepository.findByMemberIdAndProjectIdOrderByCreatedAt(memberId, projectId, pageable);
            reviews = pageReviews.getContent(); // 마지막 페이지의 내용을 가져옴
        }

        Boolean isReviewDay = checkTodayIsReviewDay(today, findProjectById(projectId).getReviewCycle());
        Boolean hasTodayReview = reviewRepository.existsReviewByReviewDateAndMemberIdAndProjectId(today, memberId, projectId);

        List<PuzzleObjectDto> result = new ArrayList<>();
        for (int idx = 1; idx <= pageSize; idx++) {
            if (idx <= reviews.size()) {
                Review review = reviews.get(idx - 1);
                result.add(PuzzleObjectDto.of(review.getReviewDate(), review.getId(), ("puzzlea" + idx)));
            } else {
                if (idx == reviews.size() + 1) {
                    if (isReviewDay && !hasTodayReview) {
                        result.add(PuzzleObjectDto.of(today, null, "puzzled" + idx));
                    } else {
                        result.add(PuzzleObjectDto.of(null, null, ("puzzlee" + idx)));
                    }
                } else {
                    result.add(PuzzleObjectDto.of(null, null, ("puzzlee" + idx)));
                }
            }
        }

        return ProjectOwnPuzzleResponseDto.of(mapperMyPuzzleObject(memberId, projectId), result,
                pageReviews.getTotalPages() - 1 < 0 ? 0 : pageReviews.getTotalPages() , isReviewDay, hasTodayReview);
    }

    @Override
    @Transactional
    public ProjectTeamPuzzleResponseDto getTeamPuzzles(Principal principal, Long projectId, String today) {
        if (today.equals("") || today == null) {
            throw new BadRequestException(ErrorStatus.VALIDATION_REQUEST_MISSING_EXCEPTION.getMessage());
        }
        Long memberId = MemberUtil.getMemberId(principal);
        Project findProject = findProjectById(projectId);
        Boolean isReviewDay = checkTodayIsReviewDay(today, findProjectById(projectId).getReviewCycle());
        Boolean hasTodayReview = reviewRepository.existsReviewByReviewDateAndMemberIdAndProjectId(today, memberId, projectId);
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
        int pageSize = 15;

        // 날짜별 리뷰 개수를 배열 형태로 변환하여 저장
        for (Map.Entry<String, Integer> entry : sortedReviewCountMap.entrySet()) {
            String reviewMemberPercent = getReviewMemberPercent(projectId, entry.getValue());
            int remainPage = (int) (idx % pageSize);
            teamPuzzleBoard.add(TeamPuzzleObjectDto.of(entry.getKey(),
                    reviewMemberPercent, "puzzle" + reviewMemberPercent + remainPage));
            idx++;
        }

        int totalPages = (int) Math.ceil((double) teamPuzzleBoard.size() / pageSize);

        int lastPageNumber = totalPages - 1;
        List<TeamPuzzleObjectDto> lastPageValues = new ArrayList<>();

        if (totalPages > 0) {
            int startIndex = lastPageNumber * pageSize;
            int endIndex = teamPuzzleBoard.size();

            lastPageValues.addAll(teamPuzzleBoard.subList(startIndex, endIndex));
        } else {
            lastPageValues.addAll(teamPuzzleBoard);
        }

        int puzzleCount = lastPageValues.size();

        if (isReviewDay) {
            if (!hasTodayReview) {
                lastPageValues.add(TeamPuzzleObjectDto.of(null, null, "puzzled" + (lastPageValues.size() + 1)));
            }
        }

        for (int i = lastPageValues.size() + 1; i <= pageSize ; i++) {
            lastPageValues.add(TeamPuzzleObjectDto.of(null, null, ("puzzlee" + i)));
        }
        return ProjectTeamPuzzleResponseDto.of(ProjectMyPuzzleObjectDto.of(findProject.getName(), puzzleCount), lastPageValues,
                lastPageNumber < 0 ? 0 : lastPageNumber, isReviewDay, hasTodayReview);
    }

    @Override
    @Transactional
    public ProjectRegisterResponseDto createProject(Long memberId, ProjectRegisterRequestDto projectRegisterRequestDto) {
        String inviteCode = makeShortUUID();
        String cycle = convertReviewCycleToString(projectRegisterRequestDto.getReviewCycle());

        Project project = Project.builder()
                .name(projectRegisterRequestDto.getProjectName())
                .intro(projectRegisterRequestDto.getProjectIntro())
                .startDate(projectRegisterRequestDto.getProjectStartDate())
                .code(inviteCode)
                .createUserId(memberId)
                .reviewCycle(cycle)
                .build();
        Project savedProject = projectRepository.save(project);

        Member member = findMemberById(memberId);

        UserProject userProject = UserProject.builder()
                .role(projectRegisterRequestDto.getMemberProjectRole())
                .nickname(projectRegisterRequestDto.getMemberProjectNickname())
                .leaderOrNot(Boolean.TRUE)
                .reviewTemplateId(1L)
                .member(member)
                .project(project)
                .build();
        userProjectRepository.save(userProject);
        return ProjectRegisterResponseDto.of(inviteCode, savedProject.getId());
    }

    public List<ProjectTeamRankResponseDto> getTeamRank(Long projectId) {
        Project projectById = findProjectById(projectId);
        List<UserProject> findUserProjects = userProjectRepository.findAllByProjectIdOrderByReviewCountDesc(projectId);
        List<ProjectTeamRankResponseDto> result = new ArrayList<>();
        int idx = 1;
        for (UserProject userProject : findUserProjects) {
            result.add(ProjectTeamRankResponseDto.of(idx++, userProject));
        }
        return result;
    }

    @Override
    @Transactional
    public ProjectJoinResponseDto joinProject(Long memberId, ProjectJoinRequestDto projectJoinRequestDto){
        if ( projectJoinRequestDto.getProjectId() == null ) {
            throw new BadRequestException("공백일 수 없습니다. (reviewTemplateId)");
        }
        if (userProjectRepository.existsByProjectIdAndNickname(projectJoinRequestDto.getProjectId(),projectJoinRequestDto.getMemberProjectNickname())){
            throw new BadRequestException(("이미 프로젝트에 있는 닉네임입니다."));
        }
//        if (userProjectRepository.existsByMemberIdAndProjectId(memberId, projectJoinRequestDto.getProjectId())){
//            throw new BadRequestException(("이미 프로젝트에 참여한 팀원입니다."));
//        }
        Member member = findMemberById(memberId);
        Project project = findProjectById(projectJoinRequestDto.getProjectId());

        UserProject userProject = UserProject.builder()
                .role(projectJoinRequestDto.getMemberProjectRole())
                .nickname(projectJoinRequestDto.getMemberProjectNickname())
                .leaderOrNot(Boolean.FALSE)
                .reviewTemplateId(1L)
                .member(member)
                .project(project)
                .build();
        userProjectRepository.save(userProject);
        return new ProjectJoinResponseDto(projectJoinRequestDto.getProjectId());
    }

    @Override
    @Transactional
    public ProjectCycleResponseDto getProjectCycle(Long projectId) {
        Project project = findProjectById(projectId);
        String name = project.getName();
        String cycle = project.getReviewCycle();

        return new ProjectCycleResponseDto(name,cycle);
    }

    private String getReviewMemberPercent(Long projectId, int reviewCount) {
        int totalProjectMember = userProjectRepository.findAllByProjectId(projectId).size();
        float percent = (float) reviewCount/totalProjectMember;

        if (percent <= 0.334) {
            return "a";
        } else if (0.334 <= percent && percent <= 0.667 ) {
            return "b";
        } else {
            return "c";
        }
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_MEMBER.getMessage()));
    }

    private Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_PROJECT.getMessage()));
    }

    private UserProject findUserProjectByMemberIdAndProjectId (Long memberId, Long projectId) {
        return userProjectRepository.findByMemberIdAndProjectId(memberId,projectId)
                .orElseThrow(() -> new NotFoundException("해당하는 멤버가 참여하는 프로젝트가 아닙니다."));
    }

    private ProjectMyPuzzleObjectDto mapperMyPuzzleObject(Long memberId, Long projectId) {
        UserProject findUserProject = findUserProjectByMemberIdAndProjectId(memberId, projectId);
        int puzzleCount = reviewRepository.findByMemberIdAndProjectId(memberId, projectId).size();
        return ProjectMyPuzzleObjectDto.of(findUserProject.getNickname(), puzzleCount % 15);
    }

    // 10자리의 UUID 생성
    public static String makeShortUUID() {
        UUID uuid = UUID.randomUUID();
        return parseToShortUUID(uuid.toString());
    }

    // 파라미터로 받은 값을 10자리의 UUID로 변환
    public static String parseToShortUUID(String uuid) {
        int l = ByteBuffer.wrap(uuid.getBytes()).getInt();
        return Integer.toString(l, 9);
    }

    public String convertReviewCycleToString(String[] array) {
        if (array == null || array.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
