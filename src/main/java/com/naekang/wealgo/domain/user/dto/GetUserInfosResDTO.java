package com.naekang.wealgo.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetUserInfosResDTO {

    private String email;

    private String username;

    private int solvedCount;

    private int[] solvedList;

}