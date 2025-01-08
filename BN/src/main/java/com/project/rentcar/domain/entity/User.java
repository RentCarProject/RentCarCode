package com.project.rentcar.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.rentcar.domain.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "memberId")  // DB 컬럼명 'member_id'에 매핑
    private String memberId;      // Java 필드명은 'memberId'

    @Column(name = "memberPw")   // DB 컬럼명 'member_pw'에 매핑
    private String memberPw;      // Java 필드명은 'memberPw'

    @Column(name = "memberNm")   // DB 컬럼명 'member_nm'에 매핑
    private String memberNm;      // Java 필드명은 'memberNm'

    @Column(name = "memberEmail")// DB 컬럼명 'member_email'에 매핑
    private String memberEmail;   // Java 필드명은 'memberEmail'

    private String contact;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Auth auth;

    @Column(name = "license_type") // 면허증 종류 (예: 1종 보통)
    private String licenseType;

    @Column(name = "license_number") // 면허 번호 (예: 11011234560)
    private String licenseNumber;

    @Column(name = "license_issue_date") // 발급일자 (예: 0000-00-00)
    private LocalDate licenseIssueDate;

    @Column(name = "license_expiry_date") // 만료일자 (예: 0000-00-00)
    private LocalDate licenseExpiryDate;

    @Column(name = "postcode")
    private String postcode;

    @Column(name = "road_address")
    private String roadAddress;

    @Column(name = "detailed_address")
    private String detailedAddress;





    // 기본 생성자
    public User() {}

    @Builder
    public User(String memberEmail, String contact, String memberId,String memberNm ,String memberPw, Role role,
                String licenseType,String licenseNumber,String licenseIssueDate,String licenseExpiryDate) {
        this.role = role;
        this.memberEmail = memberEmail;
        this.contact = contact;
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberNm = memberNm;
        this.licenseType = licenseType;
        this.licenseNumber = licenseNumber;
        this.licenseIssueDate = (licenseIssueDate != null) ? LocalDate.parse(licenseIssueDate) : null;
        this.licenseExpiryDate = (licenseExpiryDate != null) ? LocalDate.parse(licenseExpiryDate) : null;
    }

    public void update(UserRequestDto requestDto) {
        this.memberId = requestDto.getMemberId();
        this.memberPw = requestDto.getMemberPw();
        this.memberEmail = requestDto.getEmail();

    }
}
