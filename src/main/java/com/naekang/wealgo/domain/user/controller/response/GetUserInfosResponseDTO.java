package com.naekang.wealgo.domain.user.controller.response;

import com.naekang.wealgo.domain.auth.entity.User;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetUserInfosResponseDTO {

    private String email;

    private String username;

    private int solvedCount;

    private int[] solvedList;

    public static GetUserInfosResponseDTO fromUser(User user) {
        return GetUserInfosResponseDTO.builder()
            .email(user.getEmail())
            .username(user.getUsername())
            .solvedCount(user.getUserDetailInfo().getSolvedCount())
            .solvedList(Arrays.stream(user.getUserDetailInfo().getSolvedNumber().split(", "))
                .mapToInt(Integer::parseInt).toArray())
            .build();
    }

}