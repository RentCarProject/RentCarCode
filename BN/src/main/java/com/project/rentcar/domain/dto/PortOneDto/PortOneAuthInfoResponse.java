package com.project.rentcar.domain.dto.PortOneDto;

import lombok.Data;

@Data
public class PortOneAuthInfoResponse {
    private int code;  // 응답 코드
    private String message;  // 응답 메시지
    private AuthInfo response;  // 인증 정보

    @Data
    public static class AuthInfo {
        private String impUid;  // 인증 고유 ID
        private String merchantUid;  // 가맹점 주문 ID
        private String name;  // 사용자 이름
        private String phone;  // 사용자 전화번호
        private String birthday;  // 사용자 생년월일
        private boolean certified;  // 인증 여부
        private boolean foreigner;  // 외국인 여부
        private String gender;  // 성별 (옵션)
        private int certifiedAt;  // 인증 시각 (timestamp)
    }
}
