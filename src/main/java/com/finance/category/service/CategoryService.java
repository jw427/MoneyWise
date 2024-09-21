package com.finance.category.service;

import com.finance.category.domain.Category;
import com.finance.category.dto.*;
import com.finance.category.repository.CategoryRepository;
import com.finance.exception.*;
import com.finance.user.config.TokenProvider;
import com.finance.user.domain.User;
import com.finance.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    // 카테고리 생성
    @Transactional
    public CreateCategoryResponseDto createCategory(String token, CreateCategoryRequestDto requestDto) {
        // accessToken에서 회원 정보 가져오기
        User user = getUserInfo(token);
        // 해당 회원이 만든 카테고리 또는 기본 카테고리에 똑같은 카테고리명이 존재하는 경우 error
        if(categoryRepository.findByUser_UserIdAndCategoryName(user.getUserId(), requestDto.categoryName()).isPresent())
            throw new ConflictException(ErrorCode.ALREADY_EXIST_CATEGORY);
        // 카테고리 생성
        Category category = Category.builder()
                .categoryName(requestDto.categoryName())
                .user(user)
                .build();
        // DB 저장
        categoryRepository.save(category);
        // responseDto 반환
        return new CreateCategoryResponseDto(requestDto.categoryName() + "가 생성되었습니다!", category.getCreatedAt());
    }

    // 카테고리 전체 조회
    @Transactional(readOnly = true)
    public List<CategoryListResponseDto> getCategoryList(String token) {
        // accessToken에서 회원 정보 가져오기
        User user = getUserInfo(token);
        // 해당 회원이 만든 카테고리와 기본 카테고리 모두 조회
        List<Category> categoryList = categoryRepository.findByUser_UserId(user.getUserId());
        // responsedto로 변환 - user가 null인 카테고리는 기본 카테고리이므로 null 처리
        List<CategoryListResponseDto> responseDto = categoryList.stream()
                .map(list -> new CategoryListResponseDto(
                        list.getCategoryName(),
                        list.getUser() != null ? list.getUser().getUserId() : null))
                .collect(Collectors.toList());
        // responsedto 반환
        return responseDto;
    }

    // 카테고리 수정
    @Transactional
    public ModifyCategoryResponseDto modifyCategory(Long categoryId, String token, ModifyCategoryRequestDto requestDto) {
        // accessToken에서 회원 정보 가져오기
        User user = getUserInfo(token);
        // categoryId로 category 찾기
        Category category = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
        // 회원 본인이 만든 카테고리가 아닌 경우 수정 불가
        if(category.getUser() == null || !user.getUserId().equals(category.getUser().getUserId()))
            throw new ForbiddenException(ErrorCode.FORBIDDEN);
        // 카테고리 수정
        category.modifyCategory(requestDto.categoryName());
        // responseDto 반환
        return new ModifyCategoryResponseDto("카테고리명이 " + requestDto + "로 수정되었습니다.", category.getUpdatedAt());
    }

    // accessToken에서 회원 정보 가져오기
    public User getUserInfo(String token) {
        String accessToken = token.split("Bearer ")[1];
        String account = tokenProvider.getAccount(accessToken);
        return userRepository.findByAccount(account)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
