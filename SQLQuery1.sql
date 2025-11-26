-- ===========================
-- TẠO DATABASE
-- ===========================
CREATE DATABASE BlogDB;
GO

USE BlogDB;
GO

-- ===========================
-- TABLE: ROLES
-- ===========================
CREATE TABLE Roles (
    id INT IDENTITY(1,1) PRIMARY KEY,
    role_name NVARCHAR(100) NOT NULL,
    created_at DATETIME DEFAULT GETDATE()
);
GO


-- ===========================
-- TABLE: USERS
-- ===========================
CREATE TABLE Users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(100) NOT NULL UNIQUE,
    email NVARCHAR(150) NOT NULL UNIQUE,
    password_hash NVARCHAR(255) NOT NULL,
    full_name NVARCHAR(150),
    role_id INT NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME NULL,
    is_active BIT DEFAULT 1,
    FOREIGN KEY (role_id) REFERENCES Roles(id)
);
GO


-- ===========================
-- TABLE: CATEGORIES
-- ===========================
CREATE TABLE Categories (
    id INT IDENTITY(1,1) PRIMARY KEY,
    category_name NVARCHAR(150) NOT NULL,
    created_at DATETIME DEFAULT GETDATE()
);
GO


-- ===========================
-- TABLE: POSTS
-- ===========================
CREATE TABLE Posts (
    id INT IDENTITY(1,1) PRIMARY KEY,
    title NVARCHAR(255) NOT NULL,
    content NTEXT NOT NULL,
    thumbnail_url NVARCHAR(255),
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    is_published BIT DEFAULT 0,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (category_id) REFERENCES Categories(id)
);
GO


-- ===========================
-- TABLE: COMMENTS
-- ===========================
CREATE TABLE Comments (
    id INT IDENTITY(1,1) PRIMARY KEY,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    content NVARCHAR(500) NOT NULL,
    is_approved BIT DEFAULT 0,
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (post_id) REFERENCES Posts(id),
    FOREIGN KEY (user_id) REFERENCES Users(id)
);
GO


-- ===========================
-- TABLE: MEDIA
-- ===========================
CREATE TABLE Media (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    url NVARCHAR(255) NOT NULL,
    file_type NVARCHAR(50),
    file_size INT,
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(id)
);
GO
