package com.project.rentcar.controller;

import com.project.rentcar.domain.dto.UserRequestDto;
import com.project.rentcar.domain.dto.auth.AuthRequestDto;
import com.project.rentcar.domain.dto.auth.AuthResponseDto;
import com.project.rentcar.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthRestController {

    @Autowired
    private AuthService authService;

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto){
        System.out.println(authRequestDto);
        AuthResponseDto responseDto = authService.login(authRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    /** 회원가입 API */
    @PostMapping("/api/v1/auth/signup")
    public ResponseEntity<?> singUp(@RequestBody UserRequestDto requestDto) {
        System.out.println("받은 데이터: " + requestDto);
        authService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /** 토큰갱신 API */
    @GetMapping("/api/v1/auth/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or Invalid Authorization Header");
        }
        String refreshToken = authHeader.substring(7);
        String newAccessToken = authService.refreshToken(refreshToken);

        Map<String,String> response = new HashMap<>();
        response.put("accesstoken", newAccessToken);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
