package com.gialong.blog.service;

import com.gialong.blog.payload.PostDto;
import com.gialong.blog.payload.PostResponse;

public interface PostService {

    // --- CHỨC NĂNG ADMIN ---

    // SỬA: Integer -> Long
    PostDto createPost(PostDto postDto, Long userId);

    // SỬA: Integer -> Long
    PostDto updatePost(PostDto postDto, Long postId);

    // SỬA: Integer -> Long
    void deletePost(Long postId);

    // --- CHỨC NĂNG PUBLIC/VIEW ---

    // Lấy tất cả bài viết có phân trang, sắp xếp
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    // SỬA: Integer -> Long
    PostDto getPostById(Long postId);

    // SỬA: Integer -> Long (Dòng này sửa lỗi does not override trong Impl)
    PostResponse getPostsByCategoryId(Long categoryId, int pageNo, int pageSize, String sortBy, String sortDir);

    // Tìm kiếm bài viết theo từ khóa (có phân trang)
    PostResponse searchPosts(String query, int pageNo, int pageSize, String sortBy, String sortDir);
}