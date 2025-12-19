package com.gialong.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {


    @NotEmpty(message = "Username hoặc Email không được để trống")
    private String usernameOrEmail;

    @NotEmpty(message = "Mật khẩu không được để trống")
    private String password;
}