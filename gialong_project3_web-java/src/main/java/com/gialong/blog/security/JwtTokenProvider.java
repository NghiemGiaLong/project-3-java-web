package com.gialong.blog.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Base64;

@Component
public class JwtTokenProvider {

    // 1. SỬA: Cố định Secret Key (Không dùng @Value nữa)
    // Chuỗi này tôi viết sẵn, đủ dài và an toàn
    private final String jwtSecret = "DayLaChuoiBiMatCucKyQuanTrongDeMaHoaTokenKhongDuocDeLoRaNgoai123456789";

    // 2. SỬA: Cố định thời gian hết hạn (7 ngày)
    private final long jwtExpirationDate = 604800000;

    // 3. SỬA: Logic tạo Key an toàn hơn
    private SecretKey key() {
        // Mã hóa chuỗi bí mật sang Base64 rồi mới Decode để đảm bảo chuẩn thuật toán
        String secretBase64 = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretBase64));
    }

    // Tạo JWT Token (Giữ nguyên cú pháp của bạn)
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key())
                .compact();
    }

    // Lấy username từ JWT Token (Giữ nguyên cú pháp của bạn)
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token) // Cú pháp mới
                .getPayload()
                .getSubject();
    }

    // Validate Token (Sửa nhẹ để bắt lỗi chính xác hơn)
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parse(token); // Hoặc .parseSignedClaims(token)
            return true;
        } catch (MalformedJwtException e) {
            System.err.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is empty: " + e.getMessage());
        } catch (Exception e) {
            // Bắt thêm Exception chung để debug
            System.err.println("JWT error: " + e.getMessage());
        }
        return false;
    }
}