package com.project.rentcar.domain.dto;

import com.project.rentcar.domain.entity.Role;
import com.project.rentcar.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private Role role;
    private String email;
    private String contact;
    private String memberNm;
    private String memberId;
    private String memberPw;
    private String memberPwConfirm;

    public User toEntity(){

        return User.builder()
                .role(this.role)
                .memberEmail(this.email)
                .contact(contact)
                .memberId(this.memberId)
                .memberPw(this.memberPw)
                .memberNm(this.memberNm)
                .build();
    }
}
