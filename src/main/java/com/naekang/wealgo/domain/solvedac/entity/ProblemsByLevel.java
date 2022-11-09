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
public class ProblemsByLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String level;

    private int problemsCount;

    @Builder
    public ProblemsByLevel(String level, int problemsCount) {
        this.level = level;
        this.problemsCount = problemsCount;
    }
}
