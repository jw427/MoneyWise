package com.finance.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 공통
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    // 회원가입
    ALREADY_EXIST_ACCOUNT(HttpStatus.CONFLICT, "이미 존재하는 계정명입니다."),

    // 로그인
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "계정명 또는 비밀번호를 확인해주세요. 회원가입하지 않았을 경우 회원가입을 진행해주세요."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),

    // jwt
    INVALID_OR_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "세션이 만료되었습니다. 다시 로그인해주세요."),

    // 카테고리
    ALREADY_EXIST_CATEGORY(HttpStatus.CONFLICT, "이미 존재하는 카테고리입니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."),

    // 예산
    ALREADY_EXIST_BUDGET(HttpStatus.CONFLICT, "해당 카테고리의 예산이 이미 존재합니다."),
    BUDGET_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 예산입니다."),
    CANNOT_RECOMMEND_BUDGET(HttpStatus.BAD_REQUEST, "예산을 추천할 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
