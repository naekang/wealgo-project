package com.naekang.wealgo.domain.auth.controller.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.naekang.wealgo.domain.auth.controller.request.SignUpRequestDTO;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SignUpRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    public static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("회원 가입 시 이메일을 입력하지 않았을 경우")
    void emptyEmail_ThrownError_EmailMustNotEmpty() {
        //given
        SignUpRequestDTO signUpRequestDTO = SignUpRequestDTO.builder()
            .email("")
            .username("naekang")
            .password("1234qwer!")
            .checkedPassword("1234asdf!")
            .build();

        //when
        String message = validator.validate(signUpRequestDTO).stream().findFirst().get()
            .getMessage();

        //then
        assertEquals("이메일을 입력해주세요.", message);
    }

    @Test
    @DisplayName("회원 가입 시 입력한 이메일 형식이 잘못되었을 경우")
    void invalidEmail_ThrownError_EmailPatternIsWrong() {
        //given
        SignUpRequestDTO signUpRequestDTO = SignUpRequestDTO.builder()
            .email("rlawlsgh6306")
            .username("naekang")
            .password("1234qwer!")
            .checkedPassword("1234asdf!")
            .build();

        //when
        String message = validator.validate(signUpRequestDTO).stream().findFirst().get()
            .getMessage();

        //then
        assertEquals("이메일 형식이 맞지 않습니다.", message);
    }

    @Test
    @DisplayName("회원 가입 시 주어진 비밀번호 형식에 맞지 않는 경우")
    void invalidPassword_ThrownError_PasswordMustMatchPattern() {
        //given
        SignUpRequestDTO signUpRequestDTO = SignUpRequestDTO.builder()
            .email("rlawlsgh6306@gmail.com")
            .username("naekang")
            .password("1234qwer")
            .checkedPassword("1234qwer")
            .build();

        //when
        String message = validator.validate(signUpRequestDTO).stream().findFirst().get()
            .getMessage();

        //then
        assertEquals("비밀번호는 8-30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.", message);
    }

}