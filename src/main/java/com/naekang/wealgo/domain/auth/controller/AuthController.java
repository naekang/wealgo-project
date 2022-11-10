package com.naekang.wealgo.domain.auth.controller;

import com.naekang.wealgo.domain.auth.controller.request.ChangePwRequestDTO;
import com.naekang.wealgo.domain.auth.controller.request.LoginRequestDTO;
import com.naekang.wealgo.domain.auth.controller.request.SignUpRequestDTO;
import com.naekang.wealgo.domain.auth.controller.response.LoginResponseDTO;
import com.naekang.wealgo.domain.auth.controller.response.SignUpResponseDTO;
import com.naekang.wealgo.domain.auth.entity.User;
import com.naekang.wealgo.domain.auth.service.AuthService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDTO> singUp(
        @Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        User user = authService.signUp(signUpRequestDTO);

        return ResponseEntity.ok(SignUpResponseDTO.fromUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
        @Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        return ResponseEntity.ok(LoginResponseDTO.builder()
            .jwtToken(authService.login(loginRequestDTO))
            .build()
        );
    }

    @PostMapping("/change/password")
    public ResponseEntity<String> changePassword(
        @Valid @RequestBody ChangePwRequestDTO changePwRequestDTO,
        @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authService.changePassword(changePwRequestDTO, token));
    }

}
