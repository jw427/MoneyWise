package com.finance.expense.controller;

import com.finance.expense.domain.Expense;
import com.finance.expense.dto.*;
import com.finance.expense.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    // 지출 생성
    @PostMapping
    public ResponseEntity<CreateExpenseResponseDto> createExpense(@RequestHeader(value = "Authorization") String token, @Valid @RequestBody CreateExpenseRequestDto requestDto) {
        CreateExpenseResponseDto responseDto = expenseService.createExpense(token, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 지출 목록 조회
    @GetMapping
    public ResponseEntity<ExpenseTotalResponseDto> getExpenseList(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam LocalDate startAt,
            @RequestParam LocalDate endAt,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long minAmount,
            @RequestParam(required = false) Long maxAmount
    ) {
        ExpenseTotalResponseDto response = expenseService.getExpenseList(token, startAt, endAt, categoryId, minAmount, maxAmount);
        return ResponseEntity.ok().body(response);
    }

    // 지출 상세 조회
    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseDetailResponseDto> getExpenseDetail(@PathVariable Long expenseId, @RequestHeader(value = "Authorization") String token) {
        ExpenseDetailResponseDto responseDto = expenseService.getExpenseDetail(expenseId, token);
        return ResponseEntity.ok().body(responseDto);
    }

    // 지출 수정
    @PatchMapping("/{expenseId}")
    public ResponseEntity<ModifyExpenseResponseDto> modifyExpense(@PathVariable Long expenseId, @RequestHeader(value = "Authorization") String token, @Valid @RequestBody ModifyExpenseRequestDto requestDto) {
        ModifyExpenseResponseDto responseDto = expenseService.modifyExpense(expenseId, token, requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    // 지출 삭제
    @DeleteMapping("/{expenseId}")
    public ResponseEntity<DeleteExpenseResponseDto> deleteExpense(@PathVariable Long expenseId, @RequestHeader(value = "Authorization") String token) {
        DeleteExpenseResponseDto responseDto = expenseService.deleteExpense(expenseId, token);
        return ResponseEntity.ok().body(responseDto);
    }
}
