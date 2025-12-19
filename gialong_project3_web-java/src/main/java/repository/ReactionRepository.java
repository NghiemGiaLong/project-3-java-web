package com.gialong.blog.repository;

import com.gialong.blog.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    // Tìm reaction của 1 user trên 1 bài viết
    Optional<Reaction> findByPostIdAndUserId(Long postId, Long userId);
}