package com.finance.expense.service;

import com.finance.budget.repository.BudgetRepository;
import com.finance.category.domain.Category;
import com.finance.category.repository.CategoryRepository;
import com.finance.exception.BadRequestException;
import com.finance.exception.ErrorCode;
import com.finance.exception.ForbiddenException;
import com.finance.exception.NotFoundException;
import com.finance.expense.domain.Expense;
import com.finance.expense.dto.*;
import com.finance.expense.repository.ExpenseRepository;
import com.finance.user.config.TokenProvider;
import com.finance.user.domain.User;
import com.finance.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    // 지출 생성
    @Transactional
    public CreateExpenseResponseDto createExpense(String token, CreateExpenseRequestDto requestDto) {
        // accessToken에서 회원 정보 가져오기
        User user = getUserInfo(token);
        // categoryId로 category 찾기
        Category category = categoryRepository.findByCategoryId(requestDto.categoryId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
        // 해당 카테고리로 설정된 예산이 없을 경우
        if(budgetRepository.findByCategory_CategoryIdAndUser_UserId(user.getUserId(), requestDto.categoryId()).isEmpty())
            throw new BadRequestException(ErrorCode.BUDGET_NOT_EXIST);
        // 지출 생성
        Expense expense = Expense.builder()
                .amount(requestDto.amount())
                .memo(requestDto.memo())
                .expensedAt(requestDto.expensedAt())
                .excludeFromTotal(requestDto.excludeFromTotal())
                .user(user)
                .category(category)
                .build();
        // DB 저장
        expenseRepository.save(expense);
        // responseDto 반환
        return new CreateExpenseResponseDto(category.getCategoryName(), requestDto.amount(), requestDto.expensedAt(), expense.getCreatedAt(), requestDto.excludeFromTotal());
    }

    // 지출 목록 조회
    @Transactional(readOnly = true)
    public ExpenseTotalResponseDto getExpenseList(String token, LocalDate startAt, LocalDate endAt, Long categoryId, Long minAmount, Long maxAmount) {
        // accessToken에서 회원 정보 가져오기
        User user = getUserInfo(token);
        // 지출 목록 조회
        List<ExpenseListResponseDto> expenseList = expenseRepository.findExpenses(startAt, endAt, categoryId, minAmount, maxAmount, user.getUserId());
        // 지출 전체 합계 계산
        Long totalExpenseAmount = expenseList.stream()
                // 지출 합계에서 제외되지 않은 항목들만 계산
                .filter(expense -> !expense.excludeFromTotal())
                // 금액 합산
                .mapToLong(ExpenseListResponseDto::amount)
                .sum();
        // 카테고리 별 지출 합계 계산
        Map<String, Long> categoryExpenseAmount = expenseList.stream()
                // 지출 합계에서 제외되지 않은 항목들만 계산
                .filter(expense -> !expense.excludeFromTotal())
                .collect(Collectors.groupingBy(
                        // 카테고리명으로 그룹화
                        ExpenseListResponseDto::categoryName,
                        // 금액 합산
                        Collectors.summingLong(ExpenseListResponseDto::amount)
                ));
        return new ExpenseTotalResponseDto(expenseList, totalExpenseAmount, categoryExpenseAmount);
    }

    // 지출 상세 조회
    @Transactional(readOnly = true)
    public ExpenseDetailResponseDto getExpenseDetail(Long expenseId, String token) {
        // accessToken에서 회원 정보 가져오기
        User user = getUserInfo(token);
        // expenseId로 expense 찾기
        Expense expense = expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EXPENSE_NOT_FOUND));
        // 회원 본인의 지출이 아닌 경우 조회 불가
        if(!user.getUserId().equals(expense.getUser().getUserId()))
            throw new ForbiddenException(ErrorCode.FORBIDDEN);
        // responseDto 변환
        ExpenseDetailResponseDto responseDto = new ExpenseDetailResponseDto(
                expense.getCategory().getCategoryName(),
                expense.getAmount(),
                expense.getMemo(),
                expense.getExpensedAt(),
                expense.getCreatedAt(),
                expense.getUpdatedAt(),
                expense.isExcludeFromTotal()
        );
        // responseDto 반환
        return responseDto;
    }

    // 지출 수정
    @Transactional
    public ModifyExpenseResponseDto modifyExpense(Long expenseId, String token, ModifyExpenseRequestDto requestDto) {
        // accessToken에서 회원 정보 가져오기
        User user = getUserInfo(token);
        // categoryId로 category 찾기
        Category category = categoryRepository.findByCategoryId(requestDto.categoryId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
        // expenseId로 expense 찾기
        Expense expense = expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EXPENSE_NOT_FOUND));
        // 회원 본인의 지출이 아닌 경우 수정 불가
        if(!user.getUserId().equals(expense.getUser().getUserId()))
            throw new ForbiddenException(ErrorCode.FORBIDDEN);
        // 지출 수정
        expense.modifyExpense(
                requestDto.amount(),
                requestDto.memo(),
                requestDto.expensedAt(),
                requestDto.excludeFromTotal(),
                category
        );
        // responseDto 반환
        return new ModifyExpenseResponseDto(
                category.getCategoryName(),
                requestDto.amount(),
                requestDto.memo(),
                requestDto.expensedAt(),
                expense.getCreatedAt(),
                expense.getUpdatedAt(),
                requestDto.excludeFromTotal()
        );
    }

    // 지출 삭제
    @Transactional
    public DeleteExpenseResponseDto deleteExpense(Long expenseId, String token) {
        // accessToken에서 회원 정보 가져오기
        User user = getUserInfo(token);
        // expenseId로 expense 찾기
        Expense expense = expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EXPENSE_NOT_FOUND));
        // 회원 본인의 지출이 아닌 경우 삭제 불가
        if(!user.getUserId().equals(expense.getUser().getUserId()))
            throw new ForbiddenException(ErrorCode.FORBIDDEN);
        // 지출 삭제
        expense.deleteExpense();
        // responseDto 반환
        return new DeleteExpenseResponseDto("지출을 삭제했습니다.", expense.getDeletedAt());
    }

    // accessToken에서 회원 정보 가져오기
    public User getUserInfo(String token) {
        String accessToken = token.split("Bearer ")[1];
        String account = tokenProvider.getAccount(accessToken);
        return userRepository.findByAccount(account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
