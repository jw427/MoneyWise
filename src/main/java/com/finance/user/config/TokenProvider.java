package com.finance.user.config;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    @Value("${jwt.secret_key}")
    private String key;
    @Value("${jwt.access_token_expiration}")
    private long accessTokenValidTime;
    @Value("${jwt.refresh_token_expiration}")
    private long refreshTokenValidTime;

    // accessToken 생성
    public String createAccessToken(String account) {
        // 현재 시간
        Date now = new Date();
        // token 생성
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 타입
                .setClaims(Jwts.claims()
                        .setSubject(account) // 내용 sub : account
                        .setIssuedAt(now) // 내용 iat : 현재 시간
                        .setExpiration(new Date(now.getTime() + accessTokenValidTime)) // 내용 exp : 만료 시간
                )
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    // refreshToken 생성 - 회원 정보 포함 X
    public String createRefreshToken() {
        // 현재 시간
        Date now = new Date();
        // token 생성
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(Jwts.claims()
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                )
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
