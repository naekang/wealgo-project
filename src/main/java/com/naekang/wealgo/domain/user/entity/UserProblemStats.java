package com.naekang.wealgo.domain.user.entity;

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

    private int solvedProblem;

    private int exp;

    @Builder
    public UserProblemStats(String level, int solvedProblem, int exp) {
        this.level = level;
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
        return getSolvedProblem() == that.getSolvedProblem() && getExp() == that.getExp()
            && Objects.equals(getLevel(), that.getLevel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLevel(), getSolvedProblem(), getExp());
    }

}
