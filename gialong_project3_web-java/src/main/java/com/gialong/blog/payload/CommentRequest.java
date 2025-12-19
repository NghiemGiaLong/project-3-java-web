package com.gialong.blog.payload;

public class CommentRequest {
    private Long postId;

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


    public String getBody() {
        return body;
    }


    public void setBody(String body) {
        this.body = body;
    }
}