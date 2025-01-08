package com.project.rentcar.domain.service;

import com.project.rentcar.config.details.CustomUserDetails;
import com.project.rentcar.config.jwt.JwtTokenProvider;
import com.project.rentcar.domain.dto.UserRequestDto;
import com.project.rentcar.domain.dto.auth.AuthRequestDto;
import com.project.rentcar.domain.dto.auth.AuthResponseDto;
import com.project.rentcar.domain.entity.Auth;
import com.project.rentcar.domain.entity.Role;
import com.project.rentcar.domain.entity.User;
import com.project.rentcar.domain.repository.AuthRepository;
import com.project.rentcar.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthRepository authRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AuthResponseDto login(AuthRequestDto requestDto) {

        System.out.println(requestDto);
        User user = userRepository.findByMemberId(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalStateException("해당 유저를 찾을 수 없습니다" + requestDto.getMemberId()));
        if (!passwordEncoder.matches(requestDto.getMemberPw(), user.getMemberPw())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다." + requestDto.getMemberPw());
        }

        String accessToken = jwtTokenProvider.generateAccessToken(
                new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), user.getMemberPw()));
        String refreshToken = jwtTokenProvider.generateRefreshToken(
                new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), user.getMemberPw()));
        if (authRespository.existsByUser(user)) {
            Auth existingAuth = authRespository.findByUser(user)
                    .orElseThrow(() -> new IllegalStateException("유저에 대한 기존 토큰을 찾을 수 없습니다."));

            // 기존 토큰 삭제 (기존 토큰을 삭제한 후 새로운 토큰 저장)
            authRespository.delete(existingAuth);
        }

        Auth auth = authRespository.save(Auth.builder()
                .user(user)
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build());

        System.out.println(auth);

        return new AuthResponseDto(auth);



    }

    public void deleteToken(String token) {
        authRespository.deleteByAccessTokenOrRefreshToken(token);
    }

    /**
     * 회원가입
     */
    @Transactional
    public void signup(UserRequestDto requestDto) {
        System.out.println("받은 비밀번호: " + requestDto.getMemberPw());
        System.out.println("비밀번호 재확인 : " + requestDto.getMemberPwConfirm());

        if (requestDto.getMemberPw() == null || requestDto.getMemberPw().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }
        if (!requestDto.getMemberPw().equals(requestDto.getMemberPwConfirm())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }

        // 기본 권한을 ROLE_USER로 설정
        User user = requestDto.toEntity();
        user.setRole(Role.ROLE_USER);  // 명시적으로 ROLE_USER 설정

        user.setMemberPw(passwordEncoder.encode(requestDto.getMemberPw()));
        userRepository.save(user);
    }

    /**
     * 아이디 중복체크
     */

    public boolean isMemberIdDuplicate(String memberId) {
        return userRepository.existsByMemberId(memberId);
    }

    /**
     * Token 갱신
     */
    @Transactional
    public String refreshToken(String refreshToken) {
        // RefreshToken이 유효한지 확인
        if (jwtTokenProvider.validateToken(refreshToken)) {
            // 유효한 RefreshToken이면 새로운 AccessToken 발급
            return jwtTokenProvider.refreshAccessToken(refreshToken);
        }

        // RefreshToken이 만료되었거나 유효하지 않으면 예외 처리
        throw new IllegalArgumentException("Refresh Token이 만료되었습니다. 다시 로그인해주세요.");
    }


}
