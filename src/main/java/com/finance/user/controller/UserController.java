package com.finance.user.controller;

import com.finance.user.dto.SignUpRequestDto;
import com.finance.user.dto.SignUpResponseDto;
import com.finance.user.dto.UserLoginRequestDto;
import com.finance.user.dto.UserLoginResponseDto;
import com.finance.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto requestDto) {
        SignUpResponseDto responseDto = userService.signUp(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@Valid @RequestBody UserLoginRequestDto requestDto) {
        UserLoginResponseDto responseDto = userService.login(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }
}
