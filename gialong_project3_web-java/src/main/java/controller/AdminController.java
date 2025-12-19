package com.gialong.blog.controller;

import com.gialong.blog.payload.CommentDto;
import com.gialong.blog.payload.PostDto;
import com.gialong.blog.payload.UserDto;
import com.gialong.blog.service.CommentService;
import com.gialong.blog.service.PostService;
import com.gialong.blog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')") // Chỉ ADMIN mới vào được đây
public class AdminController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService; // Đã thêm final

    // Constructor Injection cho cả 3 Service
    public AdminController(PostService postService,
                           CommentService commentService,
                           UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
    }

    // -------------------------------------------------------------------
    // 1. QUẢN LÝ BÀI VIẾT
    // -------------------------------------------------------------------

    @GetMapping("/posts/unpublished")
    public ResponseEntity<List<PostDto>> getUnpublishedPosts() {
        // TODO: Uncomment khi PostService đã có hàm này
        // return ResponseEntity.ok(postService.getPostsByPublicationStatus(false));
        return ResponseEntity.ok(null);
    }

    @PutMapping("/posts/{postId}/publish")
    public ResponseEntity<PostDto> publishPost(@PathVariable Long postId) {
        // TODO: Uncomment khi PostService đã có hàm này
        // return ResponseEntity.ok(postService.updatePostStatus(postId, true));
        return ResponseEntity.ok(null);
    }

    // -------------------------------------------------------------------
    // 2. QUẢN LÝ BÌNH LUẬN
    // -------------------------------------------------------------------

    @GetMapping("/comments/unapproved")
    public ResponseEntity<List<CommentDto>> getUnapprovedComments() {
        // TODO: Uncomment khi CommentService đã có hàm này
        // return ResponseEntity.ok(commentService.getCommentsByApprovalStatus(false));
        return ResponseEntity.ok(null);
    }

    @PutMapping("/comments/{commentId}/approve")
    public ResponseEntity<CommentDto> approveComment(@PathVariable Long commentId) {
        // TODO: Uncomment khi CommentService đã có hàm này
        // return ResponseEntity.ok(commentService.updateCommentApprovalStatus(commentId, true));
        return ResponseEntity.ok(null);
    }

    // -------------------------------------------------------------------
    // 3. QUẢN LÝ NGƯỜI DÙNG (User Management)
    // -------------------------------------------------------------------

    // Lấy danh sách tất cả người dùng
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Thay đổi quyền hạn (Role)
    @PutMapping("/users/{userId}/role")
    public ResponseEntity<UserDto> updateUserRole(@PathVariable Long userId,
                                                  @RequestParam String newRoleName) {
        UserDto updatedUser = userService.updateUserRole(userId, newRoleName);
        return ResponseEntity.ok(updatedUser);
    }

    // API: Khóa hoặc Mở khóa người dùng (Toggle Lock)
    // PUT /api/admin/users/{userId}/toggle-lock
    @PutMapping("/users/{userId}/toggle-lock")
    public ResponseEntity<String> toggleUserLock(@PathVariable Long userId) {

        // SỬA QUAN TRỌNG: Gọi hàm toggleUserStatus thay vì deactivateUser
        // Hàm này sẽ tự động kiểm tra: Nếu đang mở -> khóa, nếu đang khóa -> mở
        userService.toggleUserStatus(userId);

        return ResponseEntity.ok("Cập nhật trạng thái tài khoản thành công!");
    }
}