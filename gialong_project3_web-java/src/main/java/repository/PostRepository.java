package com.gialong.blog.repository;

import com.gialong.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // --- 1. CÁC PHƯƠNG THỨC HỖ TRỢ SERVICE ---

    // Tìm bài viết theo danh mục (ID là Long)
    List<Post> findByCategoryId(Long categoryId);

    // Tìm kiếm theo tiêu đề (có phân trang)
    Page<Post> findByTitleContaining(String title, Pageable pageable);

    // --- 2. CÁC PHƯƠNG THỨC HỖ TRỢ CONTROLLER ---

    // Đếm số bài viết trong danh mục (ID là Long)
    long countByCategoryId(Long categoryId);

    // --- 3. CÁC PHƯƠNG THỨC NGHIỆP VỤ ---

    // Lấy danh sách bài đã xuất bản (Published = true)
    Page<Post> findByIsPublishedTrue(Pageable pageable);

    // Lấy bài đã xuất bản theo danh mục (ID là Long)
    Page<Post> findByCategoryIdAndIsPublishedTrue(Long categoryId, Pageable pageable);

    // Tìm kiếm bài đã xuất bản theo tiêu đề
    Page<Post> findByTitleContainingAndIsPublishedTrue(String title, Pageable pageable);

    // --- 4. GHI CHÚ (Tạm ẩn) ---
    // Phương thức này tạm ẩn vì Entity Post chưa mapping trường 'author' hoặc 'user'.
    // Khi nào mở ra nhớ đảm bảo userId là Long.
    // List<Post> findByAuthorId(Long userId);
}