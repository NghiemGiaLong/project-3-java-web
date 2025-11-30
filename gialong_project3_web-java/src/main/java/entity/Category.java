package com.gialong.blog.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Categories")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name", nullable = false, length = 150)
    private String categoryName;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Mối quan hệ 1-Nhiều (một Category có nhiều Post)
    // @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    // private Set<Post> posts;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}