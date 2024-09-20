package com.finance.category.controller;

import com.finance.category.dto.CreateCategoryRequestDto;
import com.finance.category.dto.CreateCategoryResponseDto;
import com.finance.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // 카테고리 생성
    @PostMapping
    public ResponseEntity<CreateCategoryResponseDto> createCategory(@RequestHeader(value = "Authorization")String token, @Valid @RequestBody CreateCategoryRequestDto requestDto) {
        CreateCategoryResponseDto responseDto = categoryService.createCategory(token, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
