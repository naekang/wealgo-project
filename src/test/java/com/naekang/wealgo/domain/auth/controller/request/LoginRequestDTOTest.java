package com.naekang.wealgo.domain.auth.controller.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.naekang.wealgo.domain.auth.controller.request.LoginRequestDTO;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LoginRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    public static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("로그인 시 이메일 입력을 안한 경우")
    void emptyEmail_ThrownError_EmailMustNotEmpty() {
        //given
        LoginRequestDTO request = LoginRequestDTO.builder()
            .email("")
            .password("1234qwer!")
            .build();

        //when
        String message = validator.validate(request).stream().findFirst().get().getMessage();

        //then
        assertEquals("이메일을 입력해주세요.", message);
    }

    @Test
    @DisplayName("로그인 시 입력한 이메일 형식이 잘못되었을 경우")
    void invalidEmail_ThrownError_EmailPatternIsWrong() {
        //given
        LoginRequestDTO request = LoginRequestDTO.builder()
            .email("rlawlsgh6306")
            .password("1234qwer!")
            .build();

        //when
        String message = validator.validate(request).stream().findFirst().get().getMessage();

        //then
        assertEquals("이메일 형식이 맞지 않습니다.", message);
    }

    @Test
    @DisplayName("로그인 시 비밀번호 입력을 안한 경우")
    void emptyPassword_ThrownError_PasswordMustNotEmpty() {
        //given
        LoginRequestDTO request = LoginRequestDTO.builder()
            .email("rlawlsgh6306@gmial.com")
            .password("")
            .build();

        //when
        String message = validator.validate(request).stream().findFirst().get().getMessage();

        //then
        assertEquals("비밀번호를 입력해주세요.", message);
    }

}