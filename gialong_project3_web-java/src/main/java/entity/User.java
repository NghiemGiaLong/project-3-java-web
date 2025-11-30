package com.gialong.blog.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, length = 100, unique = true)
    private String username;

    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash; // Lưu mật khẩu đã mã hóa

    @Column(name = "full_name", length = 150)
    private String fullName;

    @Column(name = "is_active")
    private Boolean isActive = true;

    // Mối quan hệ N-1: Nhiều User có 1 Role
    @ManyToOne(fetch = FetchType.EAGER) // EAGER: Tải Role cùng lúc với User
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Thiết lập giá trị mặc định (Thời gian tạo)
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Thiết lập giá trị (Thời gian cập nhật)
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}