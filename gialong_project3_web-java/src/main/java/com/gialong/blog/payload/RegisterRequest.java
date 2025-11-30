package com.gialong.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    @Size(min = 3, message = "Username must have at least 3 characters")
    private String username;

    @NotBlank
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;

    private String fullName;
}