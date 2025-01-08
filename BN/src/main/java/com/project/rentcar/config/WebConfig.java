package com.project.rentcar.config;


import com.project.rentcar.domain.Filter.JWTFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class WebConfig {

    private final JWTFilter jwtFilter;

    public WebConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public FilterRegistrationBean<JWTFilter> loggingFilter() {
        FilterRegistrationBean<JWTFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtFilter);
        // 필터를 적용할 경로 설정
        registrationBean.addUrlPatterns("/api/*");

        // 이미지 업로드 경로는 필터에서 제외
        registrationBean.addInitParameter("excludePatterns", "/uploads/*");

        return registrationBean;
    }
}
