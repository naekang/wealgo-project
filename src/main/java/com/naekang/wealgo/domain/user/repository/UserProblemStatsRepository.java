package com.naekang.wealgo.domain.user.repository;

import com.naekang.wealgo.domain.user.entity.UserProblemStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProblemStatsRepository extends JpaRepository<UserProblemStats, Long> {

}
