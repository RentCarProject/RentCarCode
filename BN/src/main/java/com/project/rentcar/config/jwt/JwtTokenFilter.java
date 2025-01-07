package com.project.rentcar.config.jwt;

import com.project.rentcar.config.details.CustomUserDetailsService;
import com.project.rentcar.domain.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 요청 헤더에서 AccessToken을 가져옴
        String accessToken = getTokenFromRequest(request);

        // AccessToken이 유효하면 인증 처리
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
            // AccessToken으로부터 사용자 인증 정보 추출
            UsernamePasswordAuthenticationToken authentication = getAuthenticationFromToken(accessToken);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // SecurityContext에 인증 정보 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (accessToken != null && !jwtTokenProvider.validateToken(accessToken)) {
            // AccessToken이 만료된 경우, RefreshToken을 사용해 새로운 AccessToken을 발급
            String refreshToken = getRefreshTokenFromRequest(request);
            if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
                // RefreshToken이 유효하면 새로운 AccessToken을 발급
                String newAccessToken = jwtTokenProvider.refreshAccessToken(refreshToken);
                // 새로 발급된 AccessToken을 Authorization 헤더에 추가
                response.setHeader("Authorization", "Bearer " + newAccessToken);

                // 새로운 AccessToken을 SecurityContext에 설정
                UsernamePasswordAuthenticationToken authentication = getAuthenticationFromToken(newAccessToken);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // RefreshToken도 만료되었으면 인증 실패 처리
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        // 필터 체인을 계속해서 진행
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String getRefreshTokenFromRequest(HttpServletRequest request) {
        // 요청에서 RefreshToken을 가져오는 방식 추가
        String refreshToken = request.getHeader("Refresh-Token");
        return refreshToken;
    }

    private UsernamePasswordAuthenticationToken getAuthenticationFromToken(String token){
        String memberId = jwtTokenProvider.getUsernameFromToken(token);

        System.out.println("Extracted memberId from token: " + memberId);  // 디버그 로그

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(memberId);

        // userDetails가 null이 아닌지 확인
        if (userDetails == null) {
            System.out.println("User not found for memberId: " + memberId);  // 디버그 로그
        } else {
            System.out.println("User found: " + userDetails.getUsername());  // 디버그 로그
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
