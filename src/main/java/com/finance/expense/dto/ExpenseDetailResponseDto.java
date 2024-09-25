package com.finance.expense.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExpenseDetailResponseDto(
        String categoryName, Long amount, String memo, LocalDate expensedAt, LocalDateTime createdAt, LocalDateTime updatedAt, boolean excludeFromTotal
) {
}
