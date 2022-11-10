package com.naekang.wealgo.domain.auth.controller.response;

import com.naekang.wealgo.domain.auth.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpResponseDTO {

    private String username;

    public static SignUpResponseDTO fromUser(User user) {
        return SignUpResponseDTO.builder()
            .username(user.getUsername())
            .build();
    }

}
