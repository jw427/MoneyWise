package com.finance.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 공통
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    // 회원가입
    ALREADY_EXIST_ACCOUNT(HttpStatus.CONFLICT, "이미 존재하는 계정명입니다.");

    private final HttpStatus status;
    private final String message;
}
