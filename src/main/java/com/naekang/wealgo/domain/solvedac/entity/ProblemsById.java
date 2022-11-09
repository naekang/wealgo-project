package com.naekang.wealgo.domain.solvedac.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProblemsById {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int problemId;

    private String title;

    private String level;

    private String representativeTag;

    private String problemUrl;

    @Builder
    public ProblemsById(int problemId, String title, String level, String representativeTag,
        String problemUrl) {
        this.problemId = problemId;
        this.title = title;
        this.level = level;
        this.representativeTag = representativeTag;
        this.problemUrl = problemUrl;
    }
}
