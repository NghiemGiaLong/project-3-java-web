package com.gialong.blog.service;

import com.gialong.blog.payload.CommentDto;
import java.util.List;

public interface CommentService {

    // SỬA: Integer -> Long (postId và userId)
    CommentDto createComment(Long postId, CommentDto commentDto, Long userId);

    // SỬA: Integer -> Long (postId)
    List<CommentDto> getCommentsByPostId(Long postId);

    // SỬA: Integer -> Long (postId, commentId)
    CommentDto getCommentById(Long postId, Long commentId);

    // SỬA: Integer -> Long (postId, commentId)
    CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest);

    // SỬA: Integer -> Long (postId, commentId)
    void deleteComment(Long postId, Long commentId);
}