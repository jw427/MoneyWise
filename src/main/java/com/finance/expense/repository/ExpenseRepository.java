package com.finance.expense.repository;

import com.finance.expense.domain.Expense;
import com.finance.expense.dto.ExpenseListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // 입력받은 정보에 해당하는 지출 조회
    @Query("SELECT new com.finance.expense.dto.ExpenseListResponseDto(" +
            "c.categoryName, e.amount, e.expensedAt, e.createdAt, e.excludeFromTotal) " +
            "FROM Expense e " +
            "JOIN Category c ON e.category.categoryId = c.categoryId " +
            "WHERE e.expensedAt BETWEEN :startAt AND :endAt " +
            "AND (:categoryId IS NULL OR e.category.categoryId = :categoryId) " +
            "AND (:minAmount IS NULL OR e.amount >= :minAmount) " +
            "AND (:maxAmount IS NULL OR e.amount <= :maxAmount) " +
            "AND e.deletedAt IS NULL " +
            "AND e.user.userId = :userId")
    List<ExpenseListResponseDto> findExpenses(
            @Param("startAt") LocalDate startAt,
            @Param("endAt") LocalDate endAt,
            @Param("categoryId") Long categoryId,
            @Param("minAmount") Long minAmount,
            @Param("maxAmount") Long maxAmount,
            @Param("userId") UUID userId);

    // 지출 식별값으로 지출 조회
    @Query("SELECT e FROM Expense e WHERE e.expenseId = :expenseId AND e.deletedAt IS NULL")
    Optional<Expense> findByExpenseId(@Param("expenseId") Long expenseId);
}
