package com.gialong.blog.service.impl;

import com.gialong.blog.entity.Role;
import com.gialong.blog.entity.User;
import com.gialong.blog.exception.BlogApiException;
import com.gialong.blog.payload.LoginDto;
import com.gialong.blog.payload.RegisterDto;
import com.gialong.blog.repository.RoleRepository;
import com.gialong.blog.repository.UserRepository;
import com.gialong.blog.security.JwtTokenProvider;
import com.gialong.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        // 1. Xác thực người dùng
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()));

        // 2. Thiết lập đối tượng xác thực vào SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Tạo JWT Token và trả về
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDto registerDto) {

        // 1. Kiểm tra Username đã tồn tại chưa
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username đã tồn tại!");
        }

        // 2. Kiểm tra Email đã tồn tại chưa
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email đã tồn tại!");
        }

        // 3. Tạo User Entity và thiết lập thông tin
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setFullName(registerDto.getFullName());

        // 4. Mã hóa mật khẩu (SỬA LỖI: dùng setPassword thay vì setPasswordHash)
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // 5. Gán Role mặc định ('ROLE_USER')
        Role userRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new BlogApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Lỗi hệ thống: Role 'ROLE_USER' chưa được tạo trong database."));

        user.setRole(userRole);

        // 6. Kích hoạt tài khoản (SỬA LỖI: dùng setActive thay vì setIsActive)
        user.setActive(true);

        // 7. Lưu User vào Database
        userRepository.save(user);

        return "Đăng ký người dùng thành công!";
    }
}