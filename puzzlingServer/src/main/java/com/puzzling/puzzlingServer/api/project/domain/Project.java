package com.puzzling.puzzlingServer.api.project.domain;

import com.puzzling.puzzlingServer.common.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 50)
    private String intro;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "review_cycle", nullable = false)
    private String reviewCycle;

    @Builder
    public Project(String name, String intro, String startDate,
                   String code, Long createUserId, String reviewCycle) {
        this.name = name;
        this.intro = intro;
        this.startDate = startDate;
        this.code = code;
        this.createUserId = createUserId;
        this.reviewCycle = reviewCycle;
    }

}
