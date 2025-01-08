package com.project.rentcar.config;

import com.project.rentcar.config.details.CustomUserDetailsService;
import com.project.rentcar.config.jwt.JwtTokenFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors((config)->{
            config.configurationSource(corsConfigurationSource());

        });

        return httpSecurity.csrf((config) -> {config.disable();})

                .authorizeHttpRequests((auth) -> {
                    auth.requestMatchers("/api/v1/auth/**","/","/getToken","/api/v1/reviews/**","/open/rentcar","/api/cars/**","/api/portone/**","/api/v1/rentals/**","/api/cars/available",
                                            "/api/v1/reviews/uploads/**").permitAll();
                    auth.requestMatchers("/api/v1/auth/refresh").authenticated();
                    //인증된 사용자만 접근할 수 있는 경로
                    auth.requestMatchers("/api/v1/user").authenticated();  // 사용자 정보 조회 (로그인 된 사용자만)

                    // 회원 수정 및 탈퇴는 인증된 사용자만 가능
                    auth.requestMatchers(HttpMethod.PUT, "/api/v1/user/update").authenticated();  // 회원 수정
                    auth.requestMatchers(HttpMethod.DELETE, "/api/v1/user/delete").authenticated();  // 회원 탈퇴

                    // 나머지 모든 경로는 인증 필요
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(this.jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "X-XSRF-TOKEN","REFRESH_TOKEN","IamportToken"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://another-allowed-origin.com"));

        config.setAllowCredentials(true); //
        return request -> config;
    }
}
