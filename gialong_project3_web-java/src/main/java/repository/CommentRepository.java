package com.gialong.blog.repository;

import com.gialong.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Lấy danh sách tất cả các bình luận thuộc về một bài viết cụ thể (Post).
     * Sắp xếp theo ngày tạo (cũ nhất trước).
     */
    // SỬA: Tham số đầu vào phải là Long
    List<Comment> findByPostId(Long postId);

    /**
     * Lấy danh sách bình luận đã được duyệt (is_approved = true) thuộc về một bài viết.
     * Cần thiết cho việc hiển thị công khai.
     */
    // SỬA: Tham số đầu vào phải là Long
    List<Comment> findByPostIdAndIsApprovedTrue(Long postId);
}