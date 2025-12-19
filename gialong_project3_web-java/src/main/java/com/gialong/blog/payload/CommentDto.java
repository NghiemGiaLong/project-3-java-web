package com.gialong.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {


    private Long id;


    @NotEmpty(message = "Nội dung bình luận không được để trống")
    @Size(max = 500, message = "Nội dung bình luận không được vượt quá 500 ký tự")
    private String body;




    private Long postId;


    private Long userId;

    private String username;

    private String email;

    private LocalDateTime createdAt;

    private Boolean isApproved;
}