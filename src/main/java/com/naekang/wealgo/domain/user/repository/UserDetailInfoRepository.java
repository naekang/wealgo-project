package com.naekang.wealgo.domain.user.repository;

import com.naekang.wealgo.domain.user.entity.UserDetailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailInfoRepository extends JpaRepository<UserDetailInfo, Long> {

}
