package com.project.rentcar.domain.Filter;

import com.project.rentcar.domain.entity.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // /main 경로로 오는 요청에 대해서만 처리
        String requestURI = request.getRequestURI();

        if (requestURI.equals("/main")) {
            String accessToken = request.getHeader("accessToken"); // 클라이언트가 보낸 accessToken 헤더 추출

            if (accessToken == null || accessToken.isEmpty()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 없습니다.");
                return;
            }

            // JWT 유효성 검증
            String username = JwtUtil.extractUsername(accessToken);
            if (username == null || JwtUtil.isTokenExpired(accessToken)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
                return;
            }

            // 유효한 토큰이면 다음 필터로 넘어감
            filterChain.doFilter(request, response);
        } else {
            // /main 외의 경로는 검증하지 않음
            filterChain.doFilter(request, response);
        }
    }
}
