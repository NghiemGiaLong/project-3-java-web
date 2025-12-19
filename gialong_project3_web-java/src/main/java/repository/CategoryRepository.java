package com.gialong.blog.repository;

import com.gialong.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // SỬA: Đổi tên hàm thành findByCategoryName để khớp với thuộc tính 'categoryName' trong Entity
    Optional<Category> findByCategoryName(String categoryName);
}