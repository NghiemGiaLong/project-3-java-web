package com.gialong.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Cần có nếu bạn muốn dùng constructor không tham số
@AllArgsConstructor // Tạo constructor với đủ 4 tham số
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String username;
    private String role;
}