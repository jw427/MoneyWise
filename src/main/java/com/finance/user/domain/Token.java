package com.finance.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.UUID;

@Getter
@RedisHash("tokens")
@AllArgsConstructor
public class Token {
    @Id
    private String refreshToken;

    private String userId;

    @TimeToLive
    private Long expiration;
}
