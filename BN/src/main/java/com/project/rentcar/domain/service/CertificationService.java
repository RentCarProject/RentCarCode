package com.project.rentcar.domain.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.rentcar.domain.dto.PortOneDto.PortOneAuthInfoResponse;
import com.project.rentcar.domain.dto.PortOneDto.TokenResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
@Slf4j
public class CertificationService {

    @Autowired
    private RestTemplate restTemplate;

    // 인증 정보를 조회하는 메서드
    public PortOneAuthInfoResponse getAuthInfo(String impUid, String accessToken) {
        String url = "https://api.iamport.kr/certifications/" + impUid;
        log.info("Received Access Token: {}", accessToken);

        try {
            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            log.info("Calling IAMPORT API with Token: {}", accessToken);

            // 요청 생성
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // IAMPORT API 호출
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            // 성공 응답 반환
            log.info("IAMPORT API Response: {}", response.getBody());


            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode responseNode = root.path("response");

                // `response` 필드만 DTO로 매핑
                PortOneAuthInfoResponse authInfo = objectMapper.treeToValue(responseNode, PortOneAuthInfoResponse.class);
                log.info("Mapped Response: {}", authInfo);
                return authInfo;
            } catch (Exception e) {
                log.error("JSON 매핑 실패: {}", e.getMessage());
                throw new RuntimeException("IAMPORT 응답 매핑 실패", e);
            }

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                log.error("Access Token이 유효하지 않음: {}", accessToken);
                throw new RuntimeException("Access Token이 만료되었거나 유효하지 않습니다.");
            } else {
                log.error("IAMPORT API 호출 실패: {}", e.getMessage());
                throw new RuntimeException("IAMPORT API 호출 중 문제가 발생했습니다.", e);
            }
        } catch (Exception e) {
            log.error("본인 인증 정보 조회 실패: impUid={}, error={}", impUid, e.getMessage());
            throw new RuntimeException("본인 인증 정보 조회 중 문제가 발생했습니다.", e);
        }
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
