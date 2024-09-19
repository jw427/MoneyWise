package com.finance.user.dto;

import jakarta.validation.constraints.NotBlank;

public record SignUpRequestDto(
        @NotBlank(message = "계정을 입력해주세요.") String account,
        @NotBlank(message = "비밀번호를 입력해주세요.") String password
) {
}
