package com.project.rentcar.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payments {
    @Id
    private String impUid;  // 결제 고유 ID

    private String merchantUid;  // 주문 고유 ID
    private String name;         // 결제자 이름
    private String phone;        // 결제자 전화번호
    private String pgProvider;   // PG사 정보
    private String pgTid;        // PG사 거래 ID
    private int certifiedAt;     // 인증 시간
    private boolean certified;   // 인증 여부


}
