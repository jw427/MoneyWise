package com.finance.expense.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ModifyExpenseResponseDto(
        String categoryName, Long amount, LocalDate expensedAt, LocalDateTime createdAt, LocalDateTime updatedAt, boolean excludeFromTotal
) {
}
