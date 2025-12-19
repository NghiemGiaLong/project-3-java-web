package com.gialong.blog.service;

import com.gialong.blog.payload.LoginDto;    // <--- ĐÃ SỬA: LoginRequest -> LoginDto
import com.gialong.blog.payload.RegisterDto; // <--- ĐÃ SỬA: RegisterRequest -> RegisterDto


public interface AuthService {

    // Đăng ký tài khoản mới. Trả về thông báo thành công.
    String register(RegisterDto registerDto); // <--- ĐÃ SỬA tên tham số

    // Đăng nhập. Trả về JWT Token.
    String login(LoginDto loginDto); // <--- ĐÃ SỬA tên tham số
}