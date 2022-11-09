package com.naekang.wealgo.domain.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int solvedCount;

    @Column(columnDefinition = "LONGTEXT")
    private String solvedNumber;

    @Builder
    public UserDetailInfo(int solvedCount, String solvedNumber) {
        this.solvedCount = solvedCount;
        this.solvedNumber = solvedNumber;
    }
}
