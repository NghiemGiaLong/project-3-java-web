package com.gialong.blog.payload;

public class CommentRequest {
    private Long postId;
    // SỬA: Đổi 'content' thành 'body' để khớp với Controller và DTO
    private String body;

    public CommentRequest() {
    }

    public CommentRequest(Long postId, String body) {
        this.postId = postId;
        this.body = body;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    // SỬA: Getter đổi thành getBody
    public String getBody() {
        return body;
    }

    // SỬA: Setter đổi thành setBody
    public void setBody(String body) {
        this.body = body;
    }
}