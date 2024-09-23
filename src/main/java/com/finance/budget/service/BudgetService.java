package com.finance.budget.service;

import com.finance.budget.domain.Budget;
import com.finance.budget.dto.*;
import com.finance.budget.repository.BudgetRepository;
import com.finance.category.domain.Category;
import com.finance.budget.dto.ModifyBudgetResponseDto;
import com.finance.category.repository.CategoryRepository;
import com.finance.exception.ConflictException;
import com.finance.exception.ErrorCode;
import com.finance.exception.ForbiddenException;
import com.finance.exception.NotFoundException;
import com.finance.user.config.TokenProvider;
import com.finance.user.domain.User;
import com.finance.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    // 예산 설정
    @Transactional
    public List<CreateBudgetResponseDto> createBudget(String token, List<CreateBudgetRequestDto> requestDtoList) {
        // accessToken에서 회원 정보 가져오기
        User user = getUserInfo(token);
        // responseDto 생성
        List<CreateBudgetResponseDto> responseDto = new ArrayList<>();
        for(CreateBudgetRequestDto requestDto : requestDtoList) {
            // categoryId로 category 찾기
            Category category = categoryRepository.findByCategoryId(requestDto.categoryId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
            // 이미 입력한 카테고리의 예산이 설정된 경우
            if(budgetRepository.findByCategory_CategoryIdAndUser_UserId(user.getUserId(), requestDto.categoryId()).isPresent())
                throw new ConflictException(ErrorCode.ALREADY_EXIST_BUDGET);
            // 예산 생성
            Budget budget = Budget.builder()
                    .amount(requestDto.amount())
                    .user(user)
                    .category(category)
                    .build();
            // DB 저장
            budgetRepository.save(budget);
            // responseDto에 추가
            responseDto.add(new CreateBudgetResponseDto(category.getCategoryName(), requestDto.amount(), budget.getCreatedAt()));
        }
        // responseDto 반환
        return responseDto;
    }

    // 예산 전체 조회
    @Transactional(readOnly = true)
    public List<BudgetListResponseDto> getBudgetList(String token) {
        // accessToken에서 회원 정보 가져오기
        User user = getUserInfo(token);
        // 회원의 예산 모두 조회
        List<Budget> budgetList = budgetRepository.findByUser_UserId(user.getUserId());
        // responsedto로 변환
        List<BudgetListResponseDto> responseDto = budgetList.stream()
                .map(list -> new BudgetListResponseDto(
                        list.getCategory().getCategoryName(),
                        list.getAmount()))
                .collect(Collectors.toList());
        // responsedto 반환
        return responseDto;
    }

    // 예산 수정
    @Transactional
    public ModifyBudgetResponseDto modifyBudget(Long budgetId, String token, ModifyBudgetRequestDto requestDto) {
        // accessToken에서 회원 정보 가져오기
        User user = getUserInfo(token);
        // budgetId로 budget 찾기
        Budget budget = budgetRepository.findByBudgetId(budgetId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BUDGET_NOT_FOUND));
        // 회원 본인이 만든 예산이 아닌 경우 수정 불가
        if(!user.getUserId().equals(budget.getUser().getUserId()))
            throw new ForbiddenException(ErrorCode.FORBIDDEN);
        // 예산 수정
        budget.modifyBudget(requestDto.amount());
        // responseDto 반환
        return new ModifyBudgetResponseDto("예산 금액이 " + requestDto.amount() + "원으로 수정되었습니다.", budget.getUpdatedAt());
    }

    // 예산 삭제
    @Transactional
    public DeleteBudgetResponseDto deleteBudget(Long budgetId, String token) {
        // accessToken에서 회원 정보 가져오기
        User user = getUserInfo(token);
        // budgetId로 budget 찾기
        Budget budget = budgetRepository.findByBudgetId(budgetId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BUDGET_NOT_FOUND));
        // 회원 본인이 만든 예산이 아닌 경우 삭제 불가
        if(!user.getUserId().equals(budget.getUser().getUserId()))
            throw new ForbiddenException(ErrorCode.FORBIDDEN);
        // 예산 삭제 - 논리 삭제
        budget.deleteBudget();
        // responseDto 반환
        return new DeleteBudgetResponseDto(budget.getCategory().getCategoryName() + "의 예산이 삭제되었습니다.", budget.getDeletedAt());
    }

    // 예산 추천
    public List<RecommendBudgetResponseDto> recommendBudget(RecommendBudgetRequestDto requestDto) {
        // 추천 받을 예산 총액의 범위 (±20%)
        Long minAmount = Math.round(requestDto.totalAmount() * 0.8);
        Long maxAmount = Math.round(requestDto.totalAmount() * 1.2);
        // 입력받은 예산 총액의 ±20% 범위 안에 예산 총액을 설정한 회원들의 기본 카테고리별 예산 평균 비율 조회
        List<BudgetRatioDto> budgetRatioDto = budgetRepository.findBudgetRatioByTotalAmountRange(minAmount, maxAmount);
        // responseDto
        List<RecommendBudgetResponseDto> responseDto = new ArrayList<>();
        // 기타 항목 계산을 위한 변수
        Long plutAmount = 0L;
        // ratio -> responseDto로 변환
        for (BudgetRatioDto ratio : budgetRatioDto) {
            // 예산 평균 비율이 0.05 이상인 경우
            if(ratio.amountRatio() >= 0.05) {
                Long recommendAmount = Math.round((requestDto.totalAmount() * ratio.amountRatio()) / 10.0) * 10;
                plutAmount += recommendAmount;
                responseDto.add(new RecommendBudgetResponseDto(ratio.categoryName(), recommendAmount));
            }
        }
        if(requestDto.totalAmount() - plutAmount > 0)
            responseDto.add(new RecommendBudgetResponseDto("기타", requestDto.totalAmount() - plutAmount));
        return responseDto;
    }

    // accessToken에서 회원 정보 가져오기
    public User getUserInfo(String token) {
        String accessToken = token.split("Bearer ")[1];
        String account = tokenProvider.getAccount(accessToken);
        return userRepository.findByAccount(account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
