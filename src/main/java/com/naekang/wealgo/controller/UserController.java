package com.naekang.wealgo.controller;

import com.naekang.wealgo.dto.SignUpRequestDTO;
import com.naekang.wealgo.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public String singUp(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) throws Exception {
        return userService.signUp(signUpRequestDTO);
    }

}
