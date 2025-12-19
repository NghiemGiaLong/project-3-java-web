package com.gialong.blog.controller;

import com.gialong.blog.payload.CommentDto;
import com.gialong.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments") // Lồng dưới Post
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // --- CHỨC NĂNG USER (Cần đăng nhập) ---

    // POST: /api/posts/{postId}/comments
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") Long postId, // SỬA: Integer -> Long
                                                    @Valid @RequestBody CommentDto commentDto) {

        // 1. Lấy thông tin người dùng từ Security Context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // TẠM THỜI DÙNG MOCK ID LÀ LONG:
        // Sau này bạn sẽ query DB để lấy ID thật từ username
        Long userId = 1L; // SỬA: int -> Long

        CommentDto createdComment = commentService.createComment(postId, commentDto, userId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    // PUT: /api/posts/{postId}/comments/{commentId}
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") Long postId,       // SỬA: Long
                                                    @PathVariable(value = "commentId") Long commentId, // SỬA: Long
                                                    @Valid @RequestBody CommentDto commentRequest) {

        CommentDto updatedComment = commentService.updateComment(postId, commentId, commentRequest);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    // DELETE: /api/posts/{postId}/comments/{commentId}
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,       // SỬA: Long
                                                @PathVariable(value = "commentId") Long commentId) { // SỬA: Long
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Xóa bình luận thành công.", HttpStatus.OK);
    }


    // --- CHỨC NĂNG PUBLIC/VIEW ---

    // GET: /api/posts/{postId}/comments
    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(value = "postId") Long postId) { // SỬA: Long
        List<CommentDto> commentDtos = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(commentDtos);
    }

    // GET: /api/posts/{postId}/comments/{commentId}
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId,       // SỬA: Long
                                                     @PathVariable(value = "commentId") Long commentId) { // SỬA: Long
        CommentDto commentDto = commentService.getCommentById(postId, commentId);
        return ResponseEntity.ok(commentDto);
    }
}