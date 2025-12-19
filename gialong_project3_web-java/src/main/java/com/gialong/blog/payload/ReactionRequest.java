// SỬA DÒNG NÀY: Dùng package payload thay vì dto
package com.gialong.blog.payload;

public class ReactionRequest {
    private Long postId;
    private String type; // LIKE, LOVE, DISLIKE...

    public ReactionRequest() {
    }

    public ReactionRequest(Long postId, String type) {
        this.postId = postId;
        this.type = type;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}