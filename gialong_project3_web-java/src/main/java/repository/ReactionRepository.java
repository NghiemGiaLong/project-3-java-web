package com.gialong.blog.repository;

import com.gialong.blog.entity.Reaction;
import com.gialong.blog.entity.ReactionType; // <--- Import Enum này
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    Optional<Reaction> findByPostIdAndUserId(Long postId, Long userId);

    // SỬA: Thay String bằng ReactionType
    long countByPostIdAndType(Long postId, ReactionType type);
}