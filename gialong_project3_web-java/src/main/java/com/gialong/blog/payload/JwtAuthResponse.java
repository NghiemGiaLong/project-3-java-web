package com.gialong.blog.payload;

import lombok.Data;

@Data
public class JwtAuthResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String role;

    // Cập nhật Constructor để nhận thêm role
    public JwtAuthResponse(String accessToken, String role) {
        this.accessToken = accessToken;
        this.role = role;
    }
}