package com.project.rentcar.config.jwt;

import com.project.rentcar.config.details.CustomUserDetails;
import com.project.rentcar.config.details.CustomUserDetailsService;
import com.project.rentcar.domain.entity.Auth;
import com.project.rentcar.domain.entity.User;
import com.project.rentcar.domain.repository.AuthRepository;
import com.project.rentcar.domain.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    @Value("${jwt.accessTokenExpirationTime}")
    private Long jwtAccessTokenExpirationTime;

    @Value("${jwt.refreshTokenExpirationTime}")
    private Long jwtRefreshTokenExpirationTime;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserRepository userRepository;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    // AccessToken 생성
    public String generateAccessToken(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Date expiryDate = new Date(new Date().getTime() + jwtAccessTokenExpirationTime);
        return Jwts.builder()
                .setSubject(customUserDetails.getUsername()) // Username이 아니라 memberId 사용
                .claim("user_email", customUserDetails.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSecretKey())
                .compact();
    }

    // RefreshToken 생성
    public String generateRefreshToken(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Date expiryDate = new Date(new Date().getTime() + jwtRefreshTokenExpirationTime);
        return Jwts.builder()
                .setSubject(customUserDetails.getUsername()) // Username이 아니라 memberId 사용
                .claim("user_email", customUserDetails.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSecretKey())
                .compact();
    }

    // JWT 토큰에서 사용자 ID(memberId) 가져오기
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // 사용자 ID(memberId)를 반환
    }

    // JWT 토큰에서 사용자 이메일 가져오기
    public String getUserEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("user_email", String.class);
    }

    // JWT 토큰 만료일 가져오기
    public Date getDateFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    // JWT 토큰 검증
    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            System.out.println("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }

    @Transactional
    // AccessToken이 만료되면 RefreshToken을 통해 새로운 AccessToken을 재발급
    public String refreshAccessToken(String refreshToken) {
        if (!validateToken(refreshToken)) {
            throw new RuntimeException("Refresh Token이 만료되었습니다.");
        }

        String memberId = getUsernameFromToken(refreshToken); // memberId로 변경
        User user = userRepository.findByMemberId(memberId) // memberId로 유저 찾기
                .orElseThrow(() -> new RuntimeException("User not found"));

        CustomUserDetails userDetails = new CustomUserDetails(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // 새로운 AccessToken 생성
        String newAccessToken = generateAccessToken(authentication);

        Auth auth = authRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Auth not found"));
        auth.updateAccessToken(newAccessToken);

        // 새 토큰 저장
        authRepository.save(auth);
        return newAccessToken;
    }

    // AccessToken 검증 및 RefreshToken으로 재발급
    public Boolean validateAndRefreshAccessToken(String accessToken, String refreshToken) {
        if (!validateToken(accessToken)) {
            System.out.println("AccessToken이 만료되었습니다. RefreshToken으로 새로운 AccessToken을 발급합니다.");
            try {
                String newAccessToken = refreshAccessToken(refreshToken);
                if (newAccessToken != null) {
                    System.out.println("새로운 AccessToken이 발급되었습니다.");
                    return true; // 새 AccessToken이 정상적으로 발급된 경우
                }
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
                return false; // RefreshToken이 만료되었거나 문제가 발생하면 false 리턴
            }
        }
        return true; // AccessToken이 유효한 경우
    }
}

