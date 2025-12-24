package com.gialong.blog.service;

import com.gialong.blog.payload.PostDto;
import com.gialong.blog.payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto, Long userId);
    PostDto updatePost(PostDto postDto, Long postId);
    void deletePost(Long postId);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long postId);
    PostResponse getPostsByCategoryId(Long categoryId, int pageNo, int pageSize, String sortBy, String sortDir);
    PostResponse searchPosts(String query, int pageNo, int pageSize, String sortBy, String sortDir);
}