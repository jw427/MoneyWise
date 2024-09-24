package com.finance.expense.controller;

import com.finance.expense.domain.Expense;
import com.finance.expense.dto.CreateExpenseRequestDto;
import com.finance.expense.dto.CreateExpenseResponseDto;
import com.finance.expense.dto.ExpenseListResponseDto;
import com.finance.expense.dto.ExpenseTotalResponseDto;
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
}
