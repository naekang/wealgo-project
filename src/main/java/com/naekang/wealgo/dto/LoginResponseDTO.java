package com.naekang.wealgo.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDTO {

    private Long userId;

    private String email;

    private String username;

    private String jwtToken;

}
