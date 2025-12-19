package com.gialong.blog.exception;

import org.springframework.http.HttpStatus;

// Lớp ngoại lệ tùy chỉnh để xử lý lỗi nghiệp vụ và trả về mã HTTP chính xác
public class BlogApiException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public BlogApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}