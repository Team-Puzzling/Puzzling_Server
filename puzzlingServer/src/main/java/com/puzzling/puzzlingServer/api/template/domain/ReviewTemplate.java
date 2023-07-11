package com.puzzling.puzzlingServer.api.template.domain;

import com.puzzling.puzzlingServer.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewTemplate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String meaning;

    @Builder
    public ReviewTemplate(Long id, String name, String meaning) {
        this.id = id;
        this.name = name;
        this.meaning = meaning;
    }
}

