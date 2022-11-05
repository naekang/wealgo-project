package com.naekang.wealgo.domain.auth.controller;

import com.naekang.wealgo.domain.auth.dto.request.LoginRequestDTO;
import com.naekang.wealgo.domain.auth.dto.request.SignUpRequestDTO;
import com.naekang.wealgo.domain.auth.dto.response.LoginResponseDTO;
import com.naekang.wealgo.domain.auth.dto.response.SignUpResponseDTO;
import com.naekang.wealgo.domain.auth.service.AuthService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponseDTO singUp(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        return authService.signUp(signUpRequestDTO);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO);
    }

}
