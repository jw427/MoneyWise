package com.finance.user.service;

import com.finance.user.domain.Role;
import com.finance.user.domain.User;
import com.finance.user.dto.SignUpRequestDto;
import com.finance.user.dto.SignUpResponseDto;
import com.finance.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 회원가입
    public SignUpResponseDto signUp(SignUpRequestDto requestDto) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.password());
        // User 생성
        User user = User.builder()
                .account(requestDto.account())
                .password(encodedPassword)
                .role(Role.ROLE_MEMBER)
                .build();
        // db 저장
        userRepository.save(user);
        // responseDto 생성 및 반환
        return new SignUpResponseDto("회원가입이 완료되었습니다.", user.getAccount());
    }
}
