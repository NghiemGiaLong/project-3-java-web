package com.gialong.blog.service;

import com.gialong.blog.payload.CategoryDto;
import java.util.List;

public interface CategoryService {

    // Thêm/Tạo danh mục mới (Chỉ Admin)
    CategoryDto addCategory(CategoryDto categoryDto);


    CategoryDto getCategory(Long categoryId);

    // Lấy tất cả danh mục
    List<CategoryDto> getAllCategories();


    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);


    void deleteCategory(Long categoryId);
}