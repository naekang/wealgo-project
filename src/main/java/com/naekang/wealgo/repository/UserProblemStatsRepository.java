package com.naekang.wealgo.repository;

import com.naekang.wealgo.domain.UserProblemStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProblemStatsRepository extends JpaRepository<UserProblemStats, Long> {

}
