package com.project.rentcar.controller.Portone;

import com.project.rentcar.config.jwt.JwtTokenProvider;
import com.project.rentcar.domain.dto.PortOneDto.PortOneAuthInfoResponse;
import com.project.rentcar.domain.entity.Payments;
import com.project.rentcar.domain.service.CertificationService;
import com.project.rentcar.domain.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;


@RestController
@Slf4j
public class PortOneController {

    @Autowired
    private CertificationService certificationService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/api/portone/getAccessToken")
    public ResponseEntity<Map<String, String>> getToken() {
        log.info("GET /getToken");
        String accessToken = certificationService.getAccessToken(); // 서버에서 액세스 토큰 발급
        log.info("본인인증토큰 : " + accessToken);

        // 액세스 토큰을 JSON 형태로 반환
        Map<String, String> response = new HashMap<>();
        response.put("access_token", accessToken);
        log.info("본인인증토큰 JSON : " + accessToken);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAuthInfo/{imp_uid}")
    public PortOneAuthInfoResponse getAuthInfo(@PathVariable("imp_uid") String impUid) {
        return certificationService.getAuthInfo(impUid);
    }

    @PostMapping("/api/portone/payments/save")
    public ResponseEntity<String> savePayment(@RequestBody Payments payment) {
        try {
            paymentService.savePayment(payment);
            return ResponseEntity.status(HttpStatus.OK).body("결제 정보가 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 정보 저장 실패: " + e.getMessage());
        }
    }


}
