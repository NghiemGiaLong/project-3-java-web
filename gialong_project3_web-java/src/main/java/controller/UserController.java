package com.gialong.blog.controller;

import com.gialong.blog.entity.User;
import com.gialong.blog.exception.ResourceNotFoundException;
import com.gialong.blog.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. Lấy danh sách tất cả người dùng (Chỉ ADMIN được xem)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<User> getAllUsers() {
        // Trong thực tế nên dùng DTO để ẩn mật khẩu, nhưng làm nhanh ta trả về User
        return userRepository.findAll();
    }

    // 2. Xóa người dùng (Chỉ ADMIN được xóa)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        // Không cho phép Admin tự xóa chính mình
        if (user.getUsername().equals("admin")) {
            return new ResponseEntity<>("Không thể xóa Super Admin!", HttpStatus.BAD_REQUEST);
        }

        userRepository.delete(user);
        return new ResponseEntity<>("Xóa người dùng thành công.", HttpStatus.OK);
    }
}