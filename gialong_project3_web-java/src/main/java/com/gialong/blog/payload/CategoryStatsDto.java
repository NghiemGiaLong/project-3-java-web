package com.gialong.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryStatsDto {
    private Integer categoryId;
    private String categoryName;
    private Long postCount; // Số lượng bài viết
}