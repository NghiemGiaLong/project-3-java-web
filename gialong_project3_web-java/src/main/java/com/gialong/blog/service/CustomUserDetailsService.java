package com.gialong.blog.service;

import com.gialong.blog.entity.User;
import com.gialong.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // <<< KHAI BÁO VÀ INJECT REPOSITORY Ở ĐÂY >>>
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        // Sử dụng userRepository để tìm kiếm
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail)
                );

        // ... (Logic xử lý Authorities đã sửa ở bước trước) ...
        String roleName = user.getRole().getRoleName();
        GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
        Set<GrantedAuthority> authorities = Set.of(authority);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                authorities
        );
    }
}