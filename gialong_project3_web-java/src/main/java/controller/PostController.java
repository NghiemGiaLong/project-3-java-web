package com.gialong.blog.controller;

import com.gialong.blog.payload.PostDto;
import com.gialong.blog.payload.PostResponse;
import com.gialong.blog.service.PostService;
import com.gialong.blog.utils.AppConstants; // Đảm bảo bạn có class này, nếu không hãy thay bằng chuỗi cứng
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // ========================================================================
    // 1. TẠO BÀI VIẾT (QUAN TRỌNG: ĐÃ SỬA ĐỂ FIX LỖI 401)
    // ========================================================================

    // Lưu ý: Tôi đã comment dòng PreAuthorize lại.
    // Điều này cho phép SecurityConfig (.permitAll) hoạt động thực sự.
    // @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        // Truyền 1L (ID của Admin) vào đây để khớp với logic trong Service
        // Đảm bảo trong Database bảng 'users' đã có dòng có id = 1
        return new ResponseEntity<>(postService.createPost(postDto, 1L), HttpStatus.CREATED);
    }

    // ========================================================================
    // 2. CÁC API KHÁC (GIỮ NGUYÊN)
    // ========================================================================

    // Lấy tất cả bài viết (Phân trang & Sắp xếp)
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    // Lấy chi tiết 1 bài viết
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // Sửa bài viết (Chỉ Admin)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,
                                              @PathVariable(name = "id") Long id) {
        PostDto postResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // Xóa bài viết (Chỉ Admin)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Xóa bài viết thành công.", HttpStatus.OK);
    }

    // Lấy bài viết theo Danh mục
    @GetMapping("/category/{id}")
    public ResponseEntity<PostResponse> getPostsByCategory(
            @PathVariable(name = "id") Long categoryId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ){
        PostResponse postResponse = postService.getPostsByCategoryId(categoryId, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(postResponse);
    }

    // Tìm kiếm bài viết
    @GetMapping("/search")
    public ResponseEntity<PostResponse> searchPosts(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ){
        return ResponseEntity.ok(postService.searchPosts(query, pageNo, pageSize, sortBy, sortDir));
    }
}