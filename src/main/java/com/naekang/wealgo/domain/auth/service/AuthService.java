package com.naekang.wealgo.domain.auth.service;

import com.naekang.wealgo.annotation.Timer;
import com.naekang.wealgo.domain.auth.dto.request.ChangePwRequestDTO;
import com.naekang.wealgo.domain.auth.dto.request.LoginRequestDTO;
import com.naekang.wealgo.domain.auth.dto.request.SignUpRequestDTO;
import com.naekang.wealgo.domain.auth.dto.response.LoginResponseDTO;
import com.naekang.wealgo.domain.auth.dto.response.SignUpResponseDTO;
import com.naekang.wealgo.domain.auth.entity.User;
import com.naekang.wealgo.domain.auth.repository.AuthRepository;
import com.naekang.wealgo.domain.auth.type.UserRole;
import com.naekang.wealgo.domain.user.dto.UserScraperDTO;
import com.naekang.wealgo.domain.user.entity.UserDetailInfo;
import com.naekang.wealgo.exception.CustomException;
import com.naekang.wealgo.exception.ErrorCode;
import com.naekang.wealgo.jwt.JwtTokenProvider;
import com.naekang.wealgo.util.scraper.BaekjoonUserScraper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthRepository authRepository;
    private final BaekjoonUserScraper baekjoonUserScraper;

    @Timer
    @Transactional
    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) {
        if (authRepository.findByEmail(signUpRequestDTO.getEmail()).isPresent()) {
            throw new CustomException("이미 가입된 회원입니다.", ErrorCode.DUPLICATED_EMAIL);
        }

        if (authRepository.findByUsername(signUpRequestDTO.getUsername()).isPresent()) {
            throw new CustomException("이미 존재하는 닉네임입니다.", ErrorCode.DUPLICATED_USERNAME);
        }

        if (!signUpRequestDTO.getPassword().equals(signUpRequestDTO.getCheckedPassword())) {
            throw new CustomException("입력된 비밀번호가 일치하지 않습니다.", ErrorCode.NOT_MATCH_PASSWORD);
        }

        UserScraperDTO solvedProblem = baekjoonUserScraper.getSolvedProblem(
            signUpRequestDTO.getUsername());

        User newUser = User.builder()
            .email(signUpRequestDTO.getEmail())
            .username(signUpRequestDTO.getUsername())
            .password(passwordEncoder.encode(signUpRequestDTO.getPassword()))
            .role(UserRole.USER)
            .userDetailInfo(UserDetailInfo.builder()
                .solvedCount(solvedProblem.getSolvedCount())
                .solvedNumber(solvedProblem.getSolvedList())
                .build())
            .build();

        authRepository.save(newUser);

        return SignUpResponseDTO.builder()
            .username(newUser.getUsername())
            .build();
    }

    @Timer
    @Transactional
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = authRepository.findByEmail(loginRequestDTO.getEmail())
            .orElseThrow(() -> new CustomException("존재하지 않는 사용자입니다.", ErrorCode.USER_NOT_FOUND));

        if (!user.checkPassword(passwordEncoder, loginRequestDTO.getPassword())) {
            throw new CustomException("비밀번호가 일치하지 않습니다.", ErrorCode.NOT_MATCH_PASSWORD);
        }

        List<String> roles = new ArrayList<>();
        roles.add(user.getRole().name());

        return LoginResponseDTO.builder()
            .userId(user.getId())
            .email(user.getEmail())
            .username(user.getUsername())
            .jwtToken(jwtTokenProvider.createToken(user.getEmail(), roles))
            .build();
    }

    @Transactional
    public String changePassword(ChangePwRequestDTO changePwRequestDTO, String token) {
        String email = jwtTokenProvider.getUserPk(token.substring(7));

        User user = authRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException("존재하지 않는 사용자입니다.", ErrorCode.USER_NOT_FOUND));

        user.changePassword(passwordEncoder.encode(changePwRequestDTO.getPassword()));

        return user.getUsername();
    }

}
