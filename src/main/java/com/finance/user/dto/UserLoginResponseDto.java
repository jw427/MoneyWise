package com.finance.user.dto;

public record UserLoginResponseDto(
        String accessToken, String refreshToken
) {
}
