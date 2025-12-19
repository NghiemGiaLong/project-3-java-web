package com.gialong.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // Tự động sinh Getters, Setters, toString...
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    // SỬA: Đổi Integer -> Long để khớp với Database và Service
    private Long id;

    // SỬA: Đổi tên 'content' -> 'body' để khớp với code trong CommentServiceImpl
    // Nếu bạn muốn giữ là 'content', hãy nhớ vào Service sửa .getBody() thành .getContent()
    @NotEmpty(message = "Nội dung bình luận không được để trống")
    @Size(max = 500, message = "Nội dung bình luận không được vượt quá 500 ký tự")
    private String body;

    // --- CÁC TRƯỜNG BỔ SUNG CHO LOGIC ---

    // SỬA: Đổi Integer -> Long (Quan trọng nhất để hết lỗi)
    private Long postId;

    // SỬA: Đổi Integer -> Long
    private Long userId;

    private String username;

    private String email; // Thêm trường này nếu muốn lưu email người comment (Guest)

    private LocalDateTime createdAt;

    private Boolean isApproved;
}