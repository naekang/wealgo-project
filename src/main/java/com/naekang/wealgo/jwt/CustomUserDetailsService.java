package com.naekang.wealgo.jwt;

import com.naekang.wealgo.domain.auth.entity.CustomUserDetails;
import com.naekang.wealgo.domain.auth.repository.AuthRepository;
import com.naekang.wealgo.exception.CustomException;
import com.naekang.wealgo.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authRepository.findByEmail(username)
            .map(CustomUserDetails::new)
            .orElseThrow(() -> new CustomException("존재하지 않는 사용자입니다.", ErrorCode.USER_NOT_FOUND));
    }

}
