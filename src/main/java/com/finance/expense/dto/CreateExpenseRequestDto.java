package com.finance.expense.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateExpenseRequestDto(
        @NotNull(message = "카테고리는 필수로 지정해야 합니다.") Long categoryId,
        @NotNull(message = "지출 금액은 필수로 지정해야 합니다.") @Positive(message = "1원부터 입력 가능합니다.") Long amount,
        @NotBlank(message = "메모를 입력해주세요.") @Size(max = 200, message = "메모는 200자 이내로 입력해주세요.") String memo,
        @NotNull(message = "지출 날짜를 입력해주세요.") @PastOrPresent(message = "지출 날짜는 현재 또는 그 이전이어야 합니다.") LocalDate expensedAt,
        @NotNull(message = "지출 합계 제외 여부를 지정해주세요.") Boolean excludeFromTotal
) {
}
