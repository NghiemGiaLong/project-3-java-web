package com.gialong.blog.service;

import com.gialong.blog.payload.CategoryDto;
import java.util.List;

public interface CategoryService {

    // Thêm/Tạo danh mục mới (Chỉ Admin)
    CategoryDto addCategory(CategoryDto categoryDto);

    // SỬA: Đổi Integer -> Long để khớp với Entity và ServiceImpl
    CategoryDto getCategory(Long categoryId);

    // Lấy tất cả danh mục
    List<CategoryDto> getAllCategories();

    // SỬA: Đổi Integer -> Long
    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);

    // SỬA: Đổi Integer -> Long
    void deleteCategory(Long categoryId);
}