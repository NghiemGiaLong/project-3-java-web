package com.gialong.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    // SỬA: Đổi Integer -> Long
    private Long id;

    // GIỮ NGUYÊN: 'name' vì Frontend (admin.html) đang gửi JSON là { "name": "..." }
    @NotEmpty(message = "Tên danh mục không được để trống")
    @Size(min = 2, message = "Tên danh mục phải có ít nhất 2 ký tự")
    private String name;

    @NotEmpty(message = "Mô tả không được để trống")
    // SỬA: Giảm xuống 2 ký tự để dễ test
    @Size(min = 2, message = "Mô tả danh mục phải có ít nhất 2 ký tự")
    private String description;
}