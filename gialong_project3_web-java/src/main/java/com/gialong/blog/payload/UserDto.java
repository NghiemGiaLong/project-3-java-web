package com.gialong.blog.payload;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String fullName;

    // Không trả về password ở đây vì lý do bảo mật
    // private String password;

    // true = Hoạt động (Không khóa), false = Bị khóa
    private boolean isActive;

    private String role;
    private LocalDateTime createdAt;
}