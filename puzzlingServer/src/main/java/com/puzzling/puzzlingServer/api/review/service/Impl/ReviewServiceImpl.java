package com.puzzling.puzzlingServer.api.review.service.Impl;

import com.puzzling.puzzlingServer.api.review.dto.ReviewTemplateGetResponseDto;
import com.puzzling.puzzlingServer.api.review.service.ReviewService;
import com.puzzling.puzzlingServer.api.template.Repository.ReviewTemplateRepository;
import com.puzzling.puzzlingServer.api.template.domain.ReviewTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewTemplateRepository reviewTemplateRepository;

    @Override
    @Transactional
    public List<ReviewTemplateGetResponseDto> getReviewTemplateAll() {
        List<ReviewTemplate> reviewTemplates = reviewTemplateRepository.findAll();
        return reviewTemplates.stream()
                .map(reviewTemplate -> ReviewTemplateGetResponseDto.of(reviewTemplate.getId(),reviewTemplate.getName(),reviewTemplate.getMeaning()))
                .collect(Collectors.toList());
    }
}
