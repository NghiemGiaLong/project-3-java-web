# project-3-java-web
 Đề tài phát triển ứng dụng web blog. 

work flow:


## gia đoạn 1: PHÂN TÍCH

## 1 — Xác định nghiệp vụ & đối tượng

Mục tiêu: định hình rõ dự án  
**Công việc:**

- Xác định 2 nhóm người dùng:
    
    - **Người dùng cuối:** đọc bài, comment, thích, đăng ký
        
    - **Quản trị (Admin):** tạo bài viết, duyệt bài, quản lý user
        
- Liệt kê chức năng:
    
    - Đăng nhập, đăng ký, đổi mật khẩu, xác thực JWT
        
    - Xem bài viết, danh mục, tìm kiếm
        
    - Comment, like, share
        
    - Trang cá nhân, avatar
        
    - Admin dashboard: CRUD bài viết, user, category

## **2 — Đặc tả yêu cầu & UML cơ bản**

**Công việc:**

- Đặc tả Use-case (Sơ đồ người dùng – hệ thống)
    
- Sơ đồ hoạt động (Activity Diagram)
    
- Sơ đồ lớp domain chính:
    
    - User
        
    - Role
        
    - Post
        
    - Category
        
    - Comment
        
    - Tag
        
    - Media

## giai đoạn 2: THIẾT KẾ KIẾN TRÚC & DATABASE

1 — Thiết kế kiến trúc Spring Boot

2 — Thiết kế Database SQL

3  — Tạo ERD + Migration

- Tạo ERD (draw.io / dbdiagram.io)
    
- Viết file migration SQL (Flyway hoặc Liquibase)
    
- Test kết nối database với Spring Boot

## GIAI ĐOẠN 3 — BACKEND SPRING BOOT

## **1 — Dựng Skeleton Project**

- Tạo project Spring Boot
    
- Cài dependency:
    
    - Spring Web
        
    - Spring Data JPA
        
    - Spring Security
        
    - JWT
        
    - Validation
        
    - MySQL/PostgreSQL driver
        
- Cấu hình application.yml

## **2 — Authentication & Authorization (JWT)**

**Chức năng:**

- Đăng ký
    
- Đăng nhập
    
- Refresh token
    
- Phân quyền: USER, ADMIN
    
- BCrypt password encoder

## **3 — Module User**

- Xem profile
    
- Chỉnh sửa thông tin
    
- Upload avatar (S3/local storage)

## **4 — Module Bài Viết (Post)**

Người dùng:

- Xem bài
    
- Xem theo category
    
- Xem theo tag
    
- Tìm kiếm (title/content)
    

Admin:

- CRUD bài viết
    
- Upload ảnh bìa
    
- Trạng thái: Draft / Published

## **5 — Comment & Like**

- User bình luận
    
- Trả lời bình luận
    
- Like/unlike bài viết

## **6 — Admin Dashboard Backend**

- CRUD User
    
- CRUD Category
    
- Quy trình duyệt bài viết
    
- Log hoạt động Admin

## **7 — Unit Test + Integration Test**

- Test Spring Boot API
    
- Test service logic
    
- Test security filter

## GIAI ĐOẠN 4 — FRONT-END

## **1 — Thiết kế UI/UX**

- Wireframe cho từng trang:
    
    - Home
        
    - Xem bài
        
    - Trang cá nhân
        
    - Đăng nhập
        
    - Admin dashboard

## **2 — Front-end người dùng cuối**

**Trang hiển thị:**

- Home + list bài viết
    
- Xem bài chi tiết
    
- Tìm kiếm theo từ khoá
    
- Category filter
    
- Comment realtime (AJAX)

## **3 — Front-end người dùng cuối**

**Trang hiển thị:**

- Home + list bài viết
    
- Xem bài chi tiết
    
- Tìm kiếm theo từ khoá
    
- Category filter
    
- Comment realtime (AJAX)

## **4 — Admin Dashboard Front-end**

- Bảng điều khiển
    
- CRUD bài viết (editor HTML – dùng CKEditor)
    
- CRUD user
    
- CRUD category
    
- Thống kê

## GIAI ĐOẠN 5 — KIỂM THỬ & HOÀN THIỆN

test các chức năng và hoàn thiện.
