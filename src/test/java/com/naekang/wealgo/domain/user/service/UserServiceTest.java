package com.naekang.wealgo.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.naekang.wealgo.domain.auth.entity.User;
import com.naekang.wealgo.domain.auth.repository.AuthRepository;
import com.naekang.wealgo.domain.user.controller.response.GetUserInfosResponseDTO;
import com.naekang.wealgo.domain.user.entity.UserDetailInfo;
import com.naekang.wealgo.exception.CustomException;
import com.naekang.wealgo.exception.ErrorCode;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private AuthRepository authRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("닉네임으로 프로필 조회 성공")
    void getUserInfosByUsername_Success() {
        //given
        UserDetailInfo userDetailInfo = UserDetailInfo.builder()
            .solvedCount(2)
            .solvedNumber("1, 2")
            .build();

        User user = User.builder()
            .email("rlawlsgh6306@gmail.com")
            .username("naekang")
            .userDetailInfo(userDetailInfo)
            .build();

        given(authRepository.findByUsername(anyString()))
            .willReturn(Optional.of(user));

        //when
        User findUser = userService.getUserInfosByUsername(
            user.getUsername());

        //then
        assertEquals(user.getUsername(), findUser.getUsername());
        assertEquals(2, findUser.getUserDetailInfo().getSolvedCount());
    }

    @Test
    @DisplayName("닉네임으로 프로필 조회 실패_해당하는 닉네임이 없는 경우")
    void getUserInfosByUsername_NotExistUsername() {
        //given
        String username = "naekang";

        given(authRepository.findByUsername(anyString()))
            .willReturn(Optional.empty());

        //when
        CustomException exception = assertThrows(CustomException.class,
            () -> userService.getUserInfosByUsername(username));

        //then
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

}