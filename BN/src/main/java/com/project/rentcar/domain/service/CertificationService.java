package com.project.rentcar.domain.service;

import com.project.rentcar.domain.dto.PortOneDto.PortOneAuthInfoResponse;
import com.project.rentcar.domain.dto.PortOneDto.TokenResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class CertificationService {

    @Autowired
    private RestTemplate restTemplate;

    // 인증 정보를 조회하는 메서드
    public PortOneAuthInfoResponse getAuthInfo(String impUid) {
        String url = "https://api.iamport.kr/certifications/" + impUid;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getAccessToken());  // 토큰 가져오기

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<PortOneAuthInfoResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, PortOneAuthInfoResponse.class);

        return response.getBody();  // 인증 정보 반환
    }

    // 토큰을 가져오는 메서드
    public String getAccessToken() {
        // 토큰을 발급받는 API 호출
        String url = "https://api.iamport.kr/users/getToken";

        // 요청 파라미터 설정
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("imp_key", "0405885762105206");  // 아임포트 API 키
        params.add("imp_secret", "BekhM7uOoGV2ddWjADd87AoGBIMEFRoo3X0KmYtDbfikTbVEBQJFqWGrzqCVtYmBRttpvnkbJGApKZrq");  // 아임포트 API 비밀 키

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        // RestTemplate을 사용하여 POST 요청을 보냄
        ResponseEntity<TokenResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, TokenResponseDto.class);

        // 응답에서 액세스 토큰을 반환
        TokenResponseDto tokenResponseDto = response.getBody();
        return tokenResponseDto.getResponse().getAccess_token();  // 토큰 반환
    }

}
