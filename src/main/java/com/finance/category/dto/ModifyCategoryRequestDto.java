package com.finance.category.dto;

import jakarta.validation.constraints.NotBlank;

public record ModifyCategoryRequestDto(
        @NotBlank(message = "수정할 카테고리명을 입력해주세요.") String categoryName
) {
}
