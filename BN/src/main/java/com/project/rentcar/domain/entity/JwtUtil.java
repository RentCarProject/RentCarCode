package com.project.rentcar.domain.entity;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static final String SECRET_KEY = "xpPbiCRxOCqKrZ1ek1MgmQlxKsXVWwno8/BPlt4KgwY="; // 비밀 키
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 15; // 15분
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

    // AccessToken 생성
    public static String generateAccessToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // RefreshToken 생성
    public static String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // JWT에서 username 추출
    public static String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // JWT에서 클레임을 추출
    private static Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)  // Token 파싱
                .getBody();
    }

    // JWT 토큰 만료 여부 확인
    public static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // 토큰 유효성 검증
    public static boolean validateToken(String token) {
        String username = extractUsername(token);  // 토큰에서 사용자 이름 추출
        return (username != null && !isTokenExpired(token));
    }
}
