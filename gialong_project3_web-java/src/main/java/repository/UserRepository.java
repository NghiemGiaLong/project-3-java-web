package com.gialong.blog.repository;

import com.gialong.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Tìm kiếm User bằng username hoặc email (dùng cho đăng nhập)
    Optional<User> findByUsernameOrEmail(String username, String email);

    // Tìm kiếm User bằng username (dùng cho UserDetailsService)
    Optional<User> findByUsername(String username);

    // Kiểm tra username/email đã tồn tại chưa (dùng cho đăng ký)
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}