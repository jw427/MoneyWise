package com.finance.user.service;

import com.finance.exception.ConflictException;
import com.finance.exception.ErrorCode;
import com.finance.exception.UnauthorizedException;
import com.finance.user.config.TokenProvider;
import com.finance.user.domain.Role;
import com.finance.user.domain.User;
import com.finance.user.dto.SignUpRequestDto;
import com.finance.user.dto.SignUpResponseDto;
import com.finance.user.dto.UserLoginRequestDto;
import com.finance.user.dto.UserLoginResponseDto;
import com.finance.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // 회원가입
    public SignUpResponseDto signUp(SignUpRequestDto requestDto) {
        // 같은 account가 존재할 경우
        if(userRepository.findByAccount(requestDto.account()).isPresent())
            throw new ConflictException(ErrorCode.ALREADY_EXIST_ACCOUNT);
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

    // 로그인
    @Transactional
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        // account로 회원 조회
        User user = userRepository.findByAccount(requestDto.account())
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.LOGIN_FAILED));
        // password 일치여부
        if(!passwordEncoder.matches(requestDto.password(), user.getPassword()))
            throw new UnauthorizedException(ErrorCode.LOGIN_FAILED);
        // refreshToken 발급 및 저장
        String refreshToken = tokenProvider.createRefreshToken(user.getUserId());
        // accessToken 발급 및 responseDto 반환
        return new UserLoginResponseDto(tokenProvider.createAccessToken(requestDto.account()), refreshToken);
    }
}
