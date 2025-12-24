-- ===========================
-- TẠO DATABASE
-- ===========================
CREATE DATABASE IF NOT EXISTS Localhost_DB;

USE Localhost_DB;

-- ===========================
-- TABLE: ROLES
-- ===========================
CREATE TABLE Roles (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(100) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);


-- ===========================
-- TABLE: USERS
-- ===========================
CREATE TABLE Users (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(150),
    role_id INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NULL,
    is_active BOOLEAN DEFAULT TRUE, -- BIT trong T-SQL tương đương BOOLEAN/TINYINT(1)
    FOREIGN KEY (role_id) REFERENCES Roles(id)
);


-- ===========================
-- TABLE: CATEGORIES
-- ===========================
CREATE TABLE Categories (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(150) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);


-- ===========================
-- TABLE: POSTS
-- ===========================
CREATE TABLE Posts (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL, -- NTEXT trong T-SQL tương đương TEXT
    thumbnail_url VARCHAR(255),
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    is_published BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (category_id) REFERENCES Categories(id)
);


-- ===========================
-- TABLE: COMMENTS
-- ===========================
CREATE TABLE Comments (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    content VARCHAR(500) NOT NULL,
    is_approved BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES Posts(id),
    FOREIGN KEY (user_id) REFERENCES Users(id)
);


-- ===========================
-- TABLE: MEDIA
-- ===========================
CREATE TABLE Media (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    url VARCHAR(255) NOT NULL,
    file_type VARCHAR(50),
    file_size INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);
-- Kiểm tra và thêm Role mặc định nếu chưa tồn tại.
-- Đây là bước quan trọng để chức năng Đăng ký và Phân quyền hoạt động.

-- ===========================
-- SEEDING CHO TABLE: ROLES
-- ===========================

-- Thêm vai trò USER
INSERT INTO Roles (id, role_name)
SELECT 1, 'USER'
WHERE NOT EXISTS (SELECT 1 FROM Roles WHERE role_name = 'USER');

-- Thêm vai trò ADMIN
INSERT INTO Roles (id, role_name)
SELECT 2, 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM Roles WHERE role_name = 'ADMIN');
SELECT id, username, role_id FROM users WHERE username = 'new_tester_final';
SELECT id, role_name FROM roles;


SELECT * FROM Roles;

-- Sửa tên USER thành ROLE_USER
UPDATE roles
SET role_name = 'ROLE_USER'
WHERE id = 1;

-- Sửa tên ADMIN thành ROLE_ADMIN
UPDATE roles
SET role_name = 'ROLE_ADMIN'
WHERE id = 2;

-- 3. Kiểm tra lại để xác nhận
SELECT id, role_name FROM roles;

USE Localhost_DB;


INSERT INTO Users (username, email, password_hash, full_name, role_id, is_active) 
VALUES (
    'admin', 
    'admin@gmail.com', 
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOcd.ptks3d.i', 
    'Super Admin', 
    2,  
    1  
);
UPDATE Users
SET password_hash = '{noop}12345'
WHERE username = 'admin';