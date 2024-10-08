package com.finance.user.controller;

import com.finance.user.dto.TokenRequestDto;
import com.finance.user.dto.TokenResponseDto;
import com.finance.user.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tokens")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/access-token")
    public ResponseEntity<TokenResponseDto> createNewAccessToken(@RequestBody TokenRequestDto requestDto) {
        TokenResponseDto responseDto = tokenService.createNewAccessToken(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
