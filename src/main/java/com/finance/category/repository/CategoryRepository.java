package com.finance.category.repository;

import com.finance.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // 회원 식별값과 카테고리명으로 카테고리 조회
    @Query("SELECT c FROM Category c WHERE (c.user IS NULL OR c.user.userId = :userId) AND c.categoryName = :categoryName AND c.deletedAt IS NULL")
    Optional<Category> findByUser_UserIdAndCategoryName(@Param("userId") UUID userId, @Param("categoryName") String categoryName);

    // 회원 식별값으로 카테고리 전체 조회
    @Query("SELECT c FROM Category c WHERE (c.user IS NULL OR c.user.userId = :userId) AND c.deletedAt IS NULL")
    List<Category> findByUser_UserId(@Param("userId") UUID userId);

    // 카테고리 식별값으로 카테고리 찾기
    @Query("SELECT c FROM Category c WHERE c.categoryId = :categoryId AND c.deletedAt IS NULL")
    Optional<Category> findByCategoryId(Long categoryId);
}
