package com.naekang.wealgo.domain.solvedac.repository;

import com.naekang.wealgo.domain.solvedac.entity.ProblemsByClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemsByClassRepository extends JpaRepository<ProblemsByClass, Long> {

}
