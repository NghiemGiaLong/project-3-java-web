package com.gialong.blog.service.impl;

import com.gialong.blog.entity.Comment;
import com.gialong.blog.entity.Post;
import com.gialong.blog.entity.User;
import com.gialong.blog.exception.ResourceNotFoundException;
import com.gialong.blog.payload.CommentDto;
import com.gialong.blog.repository.CommentRepository;
import com.gialong.blog.repository.PostRepository;
import com.gialong.blog.repository.UserRepository;
import com.gialong.blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository,
                              UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        comment.setUser(user);

        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new ResourceNotFoundException("Comment", "PostId", postId);
        }

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new ResourceNotFoundException("Comment", "PostId", postId);
        }

        comment.setContent(commentRequest.getBody());
        if (commentRequest.getIsApproved() != null) {
            comment.setIsApproved(commentRequest.getIsApproved());
        }

        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new ResourceNotFoundException("Comment", "PostId", postId);
        }

        commentRepository.delete(comment);
    }

    private CommentDto mapToDTO(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getContent());
        commentDto.setIsApproved(comment.getIsApproved());
        commentDto.setCreatedAt(comment.getCreatedAt());
        commentDto.setPostId(comment.getPost().getId());

        if (comment.getUser() != null) {
            commentDto.setUserId(comment.getUser().getId());
            commentDto.setUsername(comment.getUser().getUsername());
            commentDto.setEmail(comment.getUser().getEmail());
        }
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setContent(commentDto.getBody());
        comment.setIsApproved(commentDto.getIsApproved() != null ? commentDto.getIsApproved() : false);
        return comment;
    }
}