package com.finance.expense.dto;

import java.util.List;
import java.util.Map;

public record ExpenseTotalResponseDto(
        List<ExpenseListResponseDto> expenseList,
        Long totalExpenseAmount,
        Map<String, Long> categoryExpenseAmount
) {
}