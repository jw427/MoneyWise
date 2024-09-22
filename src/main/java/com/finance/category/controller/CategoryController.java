package com.finance.category.controller;

import com.finance.category.dto.*;
import com.finance.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 카테고리 전체 조회
    @GetMapping
    public ResponseEntity<List<CategoryListResponseDto>> getCategoryList(@RequestHeader(value = "Authorization")String token) {
        List<CategoryListResponseDto> responseDto = categoryService.getCategoryList(token);
        return ResponseEntity.ok().body(responseDto);
    }

    // 카테고리 수정
    @PatchMapping("/{categoryId}")
    public ResponseEntity<ModifyCategoryResponseDto> modifyCategory(@PathVariable Long categoryId, @RequestHeader(value = "Authorization") String token, @Valid @RequestBody ModifyCategoryRequestDto requestDto) {
        ModifyCategoryResponseDto responseDto = categoryService.modifyCategory(categoryId, token, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    // 카테고리 삭제
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<DeleteCategoryResponseDto> deleteCategory(@PathVariable Long categoryId, @RequestHeader(value = "Authorization") String token) {
        DeleteCategoryResponseDto responseDto = categoryService.deleteCategory(categoryId, token);
        return ResponseEntity.ok().body(responseDto);
    }
}
