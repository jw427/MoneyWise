package com.finance.user.service;

import com.finance.user.config.TokenProvider;
import com.finance.user.repository.TokenRepository;
import com.finance.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    // accessToken 재발급
    public void createNewAccessToken() {

    }
}
