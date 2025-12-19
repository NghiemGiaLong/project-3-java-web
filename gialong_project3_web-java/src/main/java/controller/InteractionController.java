package com.gialong.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gialong.blog.service.CommentService;
import com.gialong.blog.payload.CommentRequest;
import com.gialong.blog.payload.ReactionRequest;
import com.gialong.blog.payload.CommentDto;

@RestController
@RequestMapping("/api/interactions")
public class InteractionController {

    @Autowired
    private CommentService commentService;

    // API tạo bình luận
    @PostMapping("/comments")
    public ResponseEntity<?> createComment(@RequestBody CommentRequest request) {
        // 1. Tạo đối tượng DTO để chuyển dữ liệu
        CommentDto commentDto = new CommentDto();

        commentDto.setBody(request.getBody());

        // 2. Lấy ID bài viết
        Long postId = request.getPostId();

        // 3. Giả lập ID người dùng (hoặc lấy từ Security sau này)
        // Vì Service yêu cầu userId, ta tạm thời để cứng là 1L để test
        Long userId = 1L;


        CommentDto createdComment = commentService.createComment(postId, commentDto, userId);

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PostMapping("/reactions")
    public ResponseEntity<?> reactToPost(@RequestBody ReactionRequest request) {
        return new ResponseEntity<>("Reaction recorded", HttpStatus.OK);
    }
}