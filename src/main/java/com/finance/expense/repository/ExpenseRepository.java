package com.finance.expense.repository;

import com.finance.expense.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT e FROM Expense e WHERE e.expensedAt BETWEEN :startAt AND :endAt " +
            "AND (:categoryId IS NULL OR e.category.categoryId = :categoryId) " +
            "AND (:minAmount IS NULL OR e.amount >= :minAmount) " +
            "AND (:maxAmount IS NULL OR e.amount <= :maxAmount) " +
            "AND e.deletedAt IS NULL " +
            "AND e.user.userId = :userId")
    List<Expense> findExpenses(
            @Param("startAt") LocalDate startAt,
            @Param("endAt") LocalDate endAt,
            @Param("categoryId") Long categoryId,
            @Param("minAmount") Long minAmount,
            @Param("maxAmount") Long maxAmount,
            @Param("userId") UUID userId);
}
