package com.gialong.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;

    @NotEmpty(message = "Tiêu đề bài viết không được để trống")
    @Size(min = 2, max = 250, message = "Tiêu đề phải từ 2 đến 250 ký tự")
    private String title;

    @NotEmpty(message = "Mô tả bài viết không được để trống")
    @Size(min = 2, message = "Mô tả bài viết phải có ít nhất 2 ký tự")
    private String description;

    @NotEmpty(message = "Nội dung bài viết không được để trống")
    private String content;

    private String imageUrl;
    private String slug;
    private boolean isPublished;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long categoryId;
    private String categoryName;

    // Thông tin người đăng
    private Long userId;
    private String authorName;

    private Set<CommentDto> comments;
}