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


    private Long id;


    @NotEmpty(message = "Tên danh mục không được để trống")
    @Size(min = 2, message = "Tên danh mục phải có ít nhất 2 ký tự")
    private String name;

    @NotEmpty(message = "Mô tả không được để trống")

    @Size(min = 2, message = "Mô tả danh mục phải có ít nhất 2 ký tự")
    private String description;
}