package com.naekang.wealgo.domain.solvedac.repository;

import com.naekang.wealgo.domain.solvedac.entity.ProblemsByLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemsByLevelRepository extends JpaRepository<ProblemsByLevel, Long> {

}
