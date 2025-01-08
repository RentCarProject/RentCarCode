package com.project.rentcar.domain.dto;

import com.project.rentcar.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String memberId;
    private String memberPw;
    private String memberNm;
    private String memberEmail;
    private String role;

    public static UserDto dtoToEntity(User user){
        return UserDto.builder()
                .memberId(user.getMemberId())
                .memberPw(user.getMemberPw())
                .memberNm(user.getMemberNm())
                .memberEmail(user.getMemberEmail())
                .role(user.getRole().toString())
                .build();
    }
}
