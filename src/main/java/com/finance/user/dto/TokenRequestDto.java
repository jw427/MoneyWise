package com.finance.user.dto;

public record TokenRequestDto(
        String accessToken, String refreshToken
) {
}
