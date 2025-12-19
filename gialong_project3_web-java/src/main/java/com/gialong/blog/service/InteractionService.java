package com.gialong.blog.service;

import com.gialong.blog.entity.Post;
import com.gialong.blog.entity.User;
import com.gialong.blog.entity.Comment;
import com.gialong.blog.entity.Reaction;
import com.gialong.blog.payload.CommentRequest;
import com.gialong.blog.payload.CommentDto;
import com.gialong.blog.payload.ReactionRequest;
import com.gialong.blog.repository.PostRepository;
import com.gialong.blog.repository.UserRepository;
import com.gialong.blog.repository.CommentRepository;
import com.gialong.blog.repository.ReactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class InteractionService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    // Method tạo comment
    @Transactional
    public CommentDto createComment(CommentRequest request, Long userId) {
        // 1. Tìm bài viết theo ID (Lấy từ request)
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // 2. Tìm người dùng theo ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Tạo Entity Comment để lưu xuống DB
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);

        // --- SỬA QUAN TRỌNG TẠI ĐÂY ---
        // Entity dùng 'content', Request dùng 'body' -> Phải mapping thủ công
        comment.setContent(request.getBody()); // Sửa .setBody() -> .setContent()

        comment.setCreatedAt(LocalDateTime.now());

        // 4. Lưu xuống DB
        Comment savedComment = commentRepository.save(comment);

        // 5. Chuyển đổi từ Entity sang DTO để trả về Controller
        CommentDto dto = new CommentDto();
        dto.setId(savedComment.getId());

        // --- SỬA QUAN TRỌNG TẠI ĐÂY ---
        // Lấy 'content' từ Entity gán vào 'body' của DTO
        dto.setBody(savedComment.getContent()); // Sửa .getBody() -> .getContent()

        dto.setPostId(post.getId());
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setCreatedAt(savedComment.getCreatedAt());

        return dto;
    }

    // Bạn có thể thêm method xử lý Reaction ở đây...
}