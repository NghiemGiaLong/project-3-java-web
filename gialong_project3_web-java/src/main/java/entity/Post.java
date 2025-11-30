package com.gialong.blog.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set; // Dùng cho quan hệ 1-Nhiều (Comment)

@Entity
@Table(name = "Posts")
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    // Sử dụng @Lob cho trường NTEXT/LOB
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "thumbnail_url", length = 255)
    private String thumbnailURL;

    @Column(name = "is_published")
    private Boolean isPublished = false;

    // Quan hệ N-1: Nhiều Post thuộc về 1 User (Author)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    // Quan hệ N-1: Nhiều Post thuộc về 1 Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Quan hệ 1-N: Một Post có nhiều Comment
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments; // Cần tạo Entity Comment trước

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}