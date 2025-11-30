package com.gialong.blog.service;

// <<< BỔ SUNG CÁC IMPORT THIẾU Ở ĐÂY >>>
import com.gialong.blog.entity.User;
import com.gialong.blog.entity.Role;
// <<< KẾT THÚC BỔ SUNG >>>

import com.gialong.blog.payload.JwtAuthResponse;
import com.gialong.blog.payload.LoginRequest;
import com.gialong.blog.payload.RegisterRequest;
import com.gialong.blog.repository.RoleRepository;
import com.gialong.blog.repository.UserRepository;
import com.gialong.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // Khai báo và Injection các thành phần cần thiết
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Phương thức LOGIN (Code của bạn)
    public JwtAuthResponse login(LoginRequest loginRequest) {
        // ... (Code login đã đúng) ...
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        String username = authentication.getName();

        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(authority -> authority.getAuthority())
                .orElseThrow(() -> new RuntimeException("User role not found after authentication."));

        return new JwtAuthResponse(token, "Bearer", username, role);
    }

    // Phương thức REGISTER (Code của bạn)
    public String register(RegisterRequest registerRequest) {
        // 1. Kiểm tra username đã tồn tại chưa
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }

        // 2. Kiểm tra email đã tồn tại chưa
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        // 3. Tạo đối tượng User mới
        User user = new User(); // <<< Cần import User
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setFullName(registerRequest.getFullName());

        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));

        // 4. Gán Role mặc định (USER)
        Role role = roleRepository.findByRoleName("USER") // <<< Cần import Role
                .orElseThrow(() -> new RuntimeException("Role not found in database."));
        user.setRole(role);

        user.setIsActive(true);

        // 5. Lưu User vào Database
        userRepository.save(user);

        return "User registered successfully!";
    }
}