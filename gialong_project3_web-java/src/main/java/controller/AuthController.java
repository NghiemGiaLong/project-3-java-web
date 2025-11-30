package com.gialong.blog.controller; // Dòng này là bắt buộc cho lớp có tên

import com.gialong.blog.payload.JwtAuthResponse;
import com.gialong.blog.payload.LoginRequest;
import com.gialong.blog.payload.RegisterRequest;
import com.gialong.blog.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController { // Lớp này phải được khai báo công khai (public)

    @Autowired
    private AuthService authService;

    // API Đăng ký (Register)
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        // Gọi service để xử lý đăng ký
        String response = authService.register(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // API Đăng nhập (Login)
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Gọi service để xác thực và nhận về DTO chứa JWT token
        JwtAuthResponse response = authService.login(loginRequest);

        // Trả về response (200 OK)
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}