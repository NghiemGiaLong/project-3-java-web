package com.gialong.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data // Tự động tạo Getters, Setters, toString, v.v. (Nếu dùng Lombok)
public class LoginDto {

    // Có thể là username hoặc email
    @NotEmpty(message = "Username hoặc Email không được để trống")
    private String usernameOrEmail;

    @NotEmpty(message = "Mật khẩu không được để trống")
    private String password;
}