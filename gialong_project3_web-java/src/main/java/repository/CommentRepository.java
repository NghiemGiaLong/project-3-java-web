package com.gialong.blog.repository;

import com.gialong.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Lấy danh sách tất cả các bình luận thuộc về một bài viết.
     */
    List<Comment> findByPostId(Long postId);

    /**
     * Lấy danh sách bình luận đã được duyệt (is_approved = true).
     */
    List<Comment> findByPostIdAndIsApprovedTrue(Long postId);

    /**
     * Đếm tổng số bình luận của một bài viết.
     * (Phương thức này dùng để hiển thị số lượng comment ngoài trang chủ/danh sách)
     */
    long countByPostId(Long postId);
}