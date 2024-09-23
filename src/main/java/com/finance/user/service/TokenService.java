package com.finance.user.service;

import com.finance.exception.ErrorCode;
import com.finance.exception.NotFoundException;
import com.finance.exception.UnauthorizedException;
import com.finance.user.config.TokenProvider;
import com.finance.user.domain.Token;
import com.finance.user.domain.User;
import com.finance.user.domain.UserDetail;
import com.finance.user.dto.TokenRequestDto;
import com.finance.user.dto.TokenResponseDto;
import com.finance.user.repository.TokenRepository;
import com.finance.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    // accessToken 재발급
    public TokenResponseDto createNewAccessToken(TokenRequestDto requestDto) {
        // 입력받은 refreshToken과 매치되는 refreshToken을 redis에서 가져오기
        Token refreshToken = tokenRepository.findById(requestDto.refreshToken())
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.INVALID_OR_EXPIRED_TOKEN));
        // refreshToken의 userId로 회원 찾기
        User user = userRepository.findById(UUID.fromString(refreshToken.getUserId()))
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        // accessToken 재발급
        String newAccessToken = tokenProvider.createAccessToken(user.getAccount());
        // responseDto 반환
        return new TokenResponseDto(newAccessToken);
    }
}
