package com.finance.expense.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExpenseListResponseDto(
        String categoryName, Long amount, LocalDate expensedAt, LocalDateTime createdAt, boolean excludeFromTotal
) {
}
