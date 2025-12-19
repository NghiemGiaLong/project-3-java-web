package com.gialong.blog.payload;

import lombok.Data;
import java.util.List;

@Data
public class PostResponse {
    private List<PostDto> content; // Danh sách bài viết trong trang hiện tại
    private int pageNo;            // Số trang hiện tại (0-based)
    private int pageSize;          // Kích thước trang
    private long totalElements;     // Tổng số bài viết
    private int totalPages;        // Tổng số trang
    private boolean last;          // Phải là trang cuối cùng không
}