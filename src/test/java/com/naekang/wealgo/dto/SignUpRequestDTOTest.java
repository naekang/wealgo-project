package com.naekang.wealgo.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SignUpRequestDTOTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
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