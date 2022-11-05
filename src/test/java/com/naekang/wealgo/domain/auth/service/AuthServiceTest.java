package com.naekang.wealgo.domain.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.naekang.wealgo.domain.auth.dto.request.SignUpRequestDTO;
import com.naekang.wealgo.domain.auth.dto.response.SignUpResponseDTO;
import com.naekang.wealgo.domain.auth.entity.User;
import com.naekang.wealgo.domain.auth.repository.UserRepository;
import com.naekang.wealgo.domain.auth.type.UserRole;
import com.naekang.wealgo.exception.CustomException;
import com.naekang.wealgo.exception.ErrorCode;
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
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

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
        SignUpResponseDTO response = authService.signUp(signUpRequestDTO);

        //then
        verify(userRepository, times(1)).save(captor.capture());
        assertEquals(signUpRequestDTO.getUsername(), response.getUsername());
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
            () -> authService.signUp(signUpRequestDTO));

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
            () -> authService.signUp(signUpRequestDTO));

        //then
        assertEquals(ErrorCode.NOT_MATCH_PASSWORD.getMessage(),
            exception.getErrorCode().getMessage());
    }

}