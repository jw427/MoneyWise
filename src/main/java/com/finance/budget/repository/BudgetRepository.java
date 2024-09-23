package com.finance.budget.repository;

import com.finance.budget.domain.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    // 카테고리 식별값과 회원 식별값으로 예산 조회
    @Query("SELECT b FROM Budget b WHERE b.user.userId = :userId AND b.category.categoryId = :categoryId AND b.deletedAt IS NULL")
    Optional<Budget> findByCategory_CategoryIdAndUser_UserId(@Param("userId") UUID userId, @Param("categoryId") Long categoryId);

    // 회원 식별값으로 예산 전체 조회
    @Query("SELECT b FROM Budget b WHERE b.user.userId = :userId AND b.deletedAt IS NULL")
    List<Budget> findByUser_UserId(@Param("userId") UUID userId);
}
