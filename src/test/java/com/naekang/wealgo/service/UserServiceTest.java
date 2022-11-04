package com.naekang.wealgo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.naekang.wealgo.domain.User;
import com.naekang.wealgo.dto.SignUpRequestDTO;
import com.naekang.wealgo.exception.CustomException;
import com.naekang.wealgo.exception.ErrorCode;
import com.naekang.wealgo.repository.UserRepository;
import com.naekang.wealgo.type.UserRole;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("회원 가입 성공")
    void signup_Success() {
        //given
        SignUpRequestDTO signUpRequestDTO = SignUpRequestDTO.builder()
            .email("rlawlsgh6306@gmail.com")
            .username("naekang")
            .password("1234qwer!")
            .checkedPassword("1234qwer!")
            .build();

        given(userRepository.findByEmail(anyString()))
            .willReturn(Optional.empty());

        given(userRepository.save(any()))
            .willReturn(User.builder()
                .email("rlawlsgh6306@gmail.com")
                .username("naekang")
                .password("1234qwer!")
                .role(UserRole.USER)
                .build());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        //when
        String signupEmail = userService.signUp(signUpRequestDTO);

        //then
        verify(userRepository, times(1)).save(captor.capture());
        assertEquals(signUpRequestDTO.getEmail(), signupEmail);
    }

    @Test
    @DisplayName("회원가입 실패_중복된 이메일이 있을 경우")
    void signup_ExceptionThrown_DuplicatedEmail() {
        //given
        SignUpRequestDTO signUpRequestDTO = SignUpRequestDTO.builder()
            .email("rlawlsgh6306@gmail.com")
            .username("naekang")
            .password("1234qwer!")
            .checkedPassword("1234qwer!")
            .build();

        given(userRepository.findByEmail(anyString()))
            .willReturn(Optional.of(User.builder()
                .email("rlawlsgh6306@gmail.com")
                .build()));

        //when
        CustomException exception = assertThrows(CustomException.class,
            () -> userService.signUp(signUpRequestDTO));

        //then
        assertEquals(ErrorCode.DUPLICATED_EMAIL.getMessage(),
            exception.getErrorCode().getMessage());
    }

    @Test
    @DisplayName("회원 가입 실패_비밀번호가 일치하지 않음")
    void signup_ExceptionThrown_DoesNotMatchPassword() {
        //given
        SignUpRequestDTO signUpRequestDTO = SignUpRequestDTO.builder()
            .email("rlawlsgh6306@gmail.com")
            .username("naekang")
            .password("1234qwer!")
            .checkedPassword("1234asdf!")
            .build();

        given(userRepository.findByEmail(anyString()))
            .willReturn(Optional.empty());

        //when
        CustomException exception = assertThrows(CustomException.class,
            () -> userService.signUp(signUpRequestDTO));

        //then
        assertEquals(ErrorCode.NOT_MATCH_PASSWORD.getMessage(),
            exception.getErrorCode().getMessage());
    }

}