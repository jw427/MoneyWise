package com.finance.user.repository;

import com.finance.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    // 계정명으로 회원 조회
    Optional<User> findByAccount(String account);
}
