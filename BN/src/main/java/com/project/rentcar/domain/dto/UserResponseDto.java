package com.project.rentcar.domain.dto;

import com.project.rentcar.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDto {
    private String memberId;
    private String email;
    private String contact;
    private String role;
    private String memberNm;

    public UserResponseDto(User entity){
        this.memberId = entity.getMemberId();
        this.email = entity.getMemberEmail();
        this.contact = entity.getContact();
        this.role = entity.getRole().name();
        this.memberNm = entity.getMemberNm();
    }
}
