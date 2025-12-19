package com.gialong.blog.service.impl;

import com.gialong.blog.entity.Category;
import com.gialong.blog.exception.BlogApiException;
import com.gialong.blog.payload.CategoryDto;
import com.gialong.blog.repository.CategoryRepository;
import com.gialong.blog.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // --- HELPER METHODS: MAPPER ---
    private CategoryDto mapToDTO(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        // Sử dụng getCategoryName() khớp với Entity
        categoryDto.setName(category.getCategoryName());
        categoryDto.setDescription(category.getDescription());
        return categoryDto;
    }

    private Category mapToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        // Sử dụng setCategoryName() khớp với Entity
        category.setCategoryName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }

    // --- IMPLEMENTATION ---

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Category category = mapToEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return mapToDTO(savedCategory);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BlogApiException(HttpStatus.NOT_FOUND, "Không tìm thấy danh mục với ID: " + categoryId));
        return mapToDTO(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BlogApiException(HttpStatus.NOT_FOUND, "Không tìm thấy danh mục để cập nhật với ID: " + categoryId));

        // Cập nhật dữ liệu vào Entity
        category.setCategoryName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        Category updatedCategory = categoryRepository.save(category);
        return mapToDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BlogApiException(HttpStatus.NOT_FOUND, "Không tìm thấy danh mục để xóa với ID: " + categoryId));

        categoryRepository.delete(category);
    }
}