package com.naekang.wealgo.domain.user.controller;

import com.naekang.wealgo.domain.auth.entity.User;
import com.naekang.wealgo.domain.user.controller.response.GetUserInfosResponseDTO;
import com.naekang.wealgo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile/{username}")
    public ResponseEntity<GetUserInfosResponseDTO> getUserByUsername(
        @PathVariable String username) {

        User user = userService.getUserInfosByUsername(username);

        return ResponseEntity.ok(GetUserInfosResponseDTO.fromUser(user));
    }

}
