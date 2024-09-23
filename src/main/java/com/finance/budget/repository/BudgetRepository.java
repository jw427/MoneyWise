package com.finance.budget.repository;

import com.finance.budget.domain.Budget;
import com.finance.budget.dto.BudgetRatioDto;
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

    // 예산 식별값으로 예산 찾기
    @Query("SELECT b FROM Budget b WHERE b.budgetId = :budgetId AND b.deletedAt IS NULL")
    Optional<Budget> findByBudgetId(@Param("budgetId") Long budgetId);

    // 예산 총액이 일정 범위 안에 있는 회원들의 기본 카테고리별 평균 비율 반환
    @Query("SELECT new com.finance.budget.dto.BudgetRatioDto(c.categoryName, " +
            "CAST((SUM(b.amount) * 1.0) / SUM(SUM(b.amount)) OVER() AS DOUBLE)) " +
            "FROM Budget b " +
            "JOIN Category c ON b.category.categoryId = c.categoryId " +
            "WHERE c.user.userId IS NULL " +
            "AND b.deletedAt IS NULL " +
            "AND b.user.userId IN (" +
                    "SELECT b2.user.userId "+
                    "FROM Budget b2 " +
                    "JOIN Category c2 ON b2.category.categoryId = c2.categoryId " +
                    "WHERE b2.deletedAt IS NULL AND c2.user.userId IS NULL " +
                    "GROUP BY b2.user.userId " +
                    "HAVING SUM(b2.amount) BETWEEN :minAmount AND :maxAmount) " +
            "GROUP BY b.category.categoryId")
    List<BudgetRatioDto> findBudgetRatioByTotalAmountRange(@Param("minAmount") Long minAmount, @Param("maxAmount") Long maxAmount);
}
