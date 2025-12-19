package com.gialong.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "is_active")
    private boolean isActive = true;

    // --- CÁC TRƯỜNG MỚI THÊM ĐỂ HỖ TRỢ KHÓA TÀI KHOẢN ---
    @Column(name = "failed_attempt")
    private int failedAttempt = 0; // Mặc định là 0

    @Column(name = "lock_time")
    private LocalDateTime lockTime; // Thời điểm bị khóa

    // ----------------------------------------------------

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}