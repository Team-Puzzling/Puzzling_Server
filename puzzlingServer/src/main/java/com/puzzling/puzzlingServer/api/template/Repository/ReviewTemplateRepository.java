package com.puzzling.puzzlingServer.api.template.Repository;

import com.puzzling.puzzlingServer.api.template.domain.ReviewTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewTemplateRepository extends JpaRepository<ReviewTemplate, Long> {
    List<ReviewTemplate> findAll();

    Optional<ReviewTemplate> findById(Long TemplateId);

}
