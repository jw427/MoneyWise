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
    ALREADY_EXIST_ACCOUNT(HttpStatus.CONFLICT, "이미 존재하는 계정명입니다."),

    // 로그인
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "계정명 또는 비밀번호를 확인해주세요. 회원가입하지 않았을 경우 회원가입을 진행해주세요."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다.");

    private final HttpStatus status;
    private final String message;
}
