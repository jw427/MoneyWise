package com.finance.user.config;

import com.finance.user.domain.Token;
import com.finance.user.domain.UserDetail;
import com.finance.user.repository.TokenRepository;
import com.finance.user.service.UserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    @Value("${jwt.secret_key}")
    private String key;
    @Value("${jwt.access_token_expiration}")
    private long accessTokenValidTime;
    @Value("${jwt.refresh_token_expiration}")
    private long refreshTokenValidTime;

    private final String BEARER_PREFIX = "Bearer ";

    private final TokenRepository tokenRepository;
    private final UserDetailService userDetailService;

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
    public String createRefreshToken(UUID userId) {
        // 현재 시간
        Date now = new Date();
        // token 생성
        String refreshToken = Jwts.builder()
                            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                            .setClaims(Jwts.claims()
                                    .setIssuedAt(now)
                                    .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                            )
                            .signWith(SignatureAlgorithm.HS256, key)
                            .compact();
        // token 객체 생성
        Token token = new Token(refreshToken, userId.toString(), refreshTokenValidTime / 1000);
        // redis 저장
        tokenRepository.save(token);
        return refreshToken;
    }

    // Header에서 토큰 가져오는 메서드
    public String resolveToken(HttpServletRequest request) {
        // Header의 Authorization 값 가져오기
        String header = request.getHeader("Authorization");
        // header 값이 null이 아니고 BEARER_PREFIX 값으로 시작하면 BEARER_PREFIX 이후의 값으로 반환
        if(header != null && header.startsWith(BEARER_PREFIX))
            return header.substring(BEARER_PREFIX.length());
        return null;
    }

    // 토큰 유효성 검증
    public boolean validToken(String token) {
        try {
            Jwts.parser().setSigningKey(key) // 토큰 검증하는데 사용되는 서명키 설정
                    .parseClaimsJws(token); // 토큰 파싱하고 서명 유효한지 확인
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // accessToken으로 인증 정보 담은 Authentication 반환
    public Authentication getAuthentication(String token) {
        UserDetail userDetail = (UserDetail) userDetailService.loadUserByUsername(getAccount(token));
        return new UsernamePasswordAuthenticationToken(userDetail, "", userDetail.getAuthorities());
        /* principal : 인증된 사용자 정보
           credentials : 사용자의 인증 자격 증명 (인증 완료된 상태이므로 빈 문자열 사용)
           authorities : 사용자의 권한목록 */
    }

    // accessToken에서 account 추출
    public String getAccount(String token) {
        try {
            // JWT를 파싱해서 JWT 서명 검증 후 클레임을 반환하여 payload에서 subject 클레임 추출
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }
}
