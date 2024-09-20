package com.finance.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequestDto(
        @NotBlank(message = "카테고리명을 입력해주세요.") String categoryName
) {
}
