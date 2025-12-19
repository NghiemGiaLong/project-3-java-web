package com.gialong.blog.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            // 1. Lấy token từ request
            String token = getTokenFromRequest(request);

            if (token == null) {
                // Nếu không có token thì thôi, để các filter sau xử lý (hoặc chặn ở SecurityConfig)
                // System.out.println("DEBUG: Không tìm thấy Token trong Header");
            } else {
                System.out.println("DEBUG: Tìm thấy Token: " + token.substring(0, 10) + "...");

                // 2. Validate Token
                boolean isValid = jwtTokenProvider.validateToken(token);
                System.out.println("DEBUG: Kết quả validateToken: " + isValid);

                if (StringUtils.hasText(token) && isValid) {

                    // 3. Lấy username
                    String username = jwtTokenProvider.getUsername(token);
                    System.out.println("DEBUG: Username từ Token: " + username);

                    // 4. Load User từ DB
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (userDetails != null) {
                        System.out.println("DEBUG: Tìm thấy User trong DB với quyền: " + userDetails.getAuthorities());

                        // 5. Set Authentication
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        System.out.println("DEBUG: Đã set SecurityContext thành công!");
                    } else {
                        System.out.println("DEBUG: Không tìm thấy UserDetails trong DB!");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("DEBUG ERROR: Lỗi nghiêm trọng trong Filter!");
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        // System.out.println("DEBUG: Header Authorization nhận được: " + bearerToken);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}