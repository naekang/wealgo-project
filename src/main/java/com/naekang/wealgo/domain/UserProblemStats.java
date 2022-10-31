package com.naekang.wealgo.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "USER_PROBLEM_STATS")
@Getter
@ToString
@NoArgsConstructor
public class UserProblemStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String level;

    private int totalProblem;

    private int solvedProblem;

    private int exp;

    @Builder
    public UserProblemStats(String level, int totalProblem, int solvedProblem, int exp) {
        this.level = level;
        this.totalProblem = totalProblem;
        this.solvedProblem = solvedProblem;
        this.exp = exp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProblemStats that)) {
            return false;
        }
        return getTotalProblem() == that.getTotalProblem()
            && getSolvedProblem() == that.getSolvedProblem() && getExp() == that.getExp()
            && Objects.equals(getLevel(), that.getLevel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLevel(), getTotalProblem(), getSolvedProblem(), getExp());
    }

}