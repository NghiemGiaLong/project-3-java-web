package com.gialong.blog.repository;

import com.gialong.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Tìm User bằng username.
     */
    Optional<User> findByUsername(String username);

    /**
     * Tìm User bằng email.
     */
    Optional<User> findByEmail(String email);

    /**
     * Tìm User bằng username HOẶC email.
     * Quan trọng cho chức năng đăng nhập (cho phép nhập cả 2).
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

    /**
     * Kiểm tra nhanh xem username hoặc email đã tồn tại chưa.
     */
    Boolean existsByUsernameOrEmail(String username, String email);

    /**
     * Kiểm tra riêng Username có tồn tại không (Dùng để báo lỗi cụ thể khi Đăng ký).
     */
    Boolean existsByUsername(String username);

    /**
     * Kiểm tra riêng Email có tồn tại không (Dùng để báo lỗi cụ thể khi Đăng ký).
     */
    Boolean existsByEmail(String email);
}