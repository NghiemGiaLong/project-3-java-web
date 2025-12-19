package com.gialong.blog.service;

import com.gialong.blog.entity.User;
import com.gialong.blog.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// <<< SỬA IMPORT >>>
import com.gialong.blog.service.CustomUserDetails; // Hoặc .security nếu bạn đặt ở đó

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Tìm kiếm User theo username hoặc email
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy User với username hoặc email: " + usernameOrEmail));

        // <<< SỬ DỤNG CLASS CUSTOMUSERDETAILS VỪA TẠO >>>
        return new CustomUserDetails(user);
    }
}