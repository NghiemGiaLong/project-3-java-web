package com.gialong.blog.controller;

import com.gialong.blog.payload.JwtAuthResponse;
import com.gialong.blog.payload.LoginDto;
import com.gialong.blog.payload.RegisterDto;
import com.gialong.blog.security.JwtTokenProvider; // Cần import cái này
import com.gialong.blog.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    // Inject thêm AuthenticationManager và JwtTokenProvider
    public AuthController(AuthService authService,
                          AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Endpoint: /api/auth/login
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        // 1. Xác thực username/password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );

        // 2. Lưu thông tin authentication vào Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Tạo Token từ thông tin authentication
        String token = jwtTokenProvider.generateToken(authentication);

        // 4. Lấy Role của user (Đây là bước quan trọng để fix lỗi)
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(item -> item.getAuthority())
                .orElse("ROLE_USER");

        // 5. Trả về Token kèm Role
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(token, role);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    // Endpoint: /api/auth/register
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}