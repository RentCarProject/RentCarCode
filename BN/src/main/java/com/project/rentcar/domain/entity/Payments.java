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

    private String merchantUid;
    private String name;
    private String phone;
    private String pgProvider;
    private String pgTid;
    private int certifiedAt;
    private boolean certified;


}
