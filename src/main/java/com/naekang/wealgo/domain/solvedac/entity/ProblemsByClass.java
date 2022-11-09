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
public class ProblemsByClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int classLevel;

    private int problemsCount;

    private int essentialCount;

    @Builder
    public ProblemsByClass(int classLevel, int problemsCount, int essentialCount) {
        this.classLevel = classLevel;
        this.problemsCount = problemsCount;
        this.essentialCount = essentialCount;
    }
}
