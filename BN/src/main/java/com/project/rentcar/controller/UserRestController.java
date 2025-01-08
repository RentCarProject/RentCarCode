package com.project.rentcar.controller;

import com.project.rentcar.config.jwt.JwtTokenProvider;
import com.project.rentcar.domain.dto.DrivceLicenseDto;
import com.project.rentcar.domain.dto.PasswordUpdateDto;
import com.project.rentcar.domain.dto.UserRequestDto;
import com.project.rentcar.domain.dto.UserResponseDto;
import com.project.rentcar.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    /** 회원정보 조회 API */
    @GetMapping("/api/v1/user")
    public ResponseEntity<?> findUser(@RequestHeader("Authorization") String accessToken) {
        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token format");
        }
        String token = accessToken.substring(7); // "Bearer " 부분을 제외한 실제 토큰만 추출
        String id = this.jwtTokenProvider.getUsernameFromToken(token);
        UserResponseDto userResponseDto = this.userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }


    /** 회원정보 수정 API */
    @PutMapping("/api/v1/user/update")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String accessToken,
                                        @RequestBody UserRequestDto requestDto) {
        String id = this.jwtTokenProvider.getUsernameFromToken(accessToken.substring(7));
        this.userService.update(id, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /** 회원정보 삭제 API */
    @DeleteMapping("/api/v1/user/delete")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String accessToken) {
        String id = this.jwtTokenProvider.getUsernameFromToken(accessToken.substring(7));
        this.userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/api/v1/user/license")
    public ResponseEntity<?> updateLicenseAndAddress(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody DrivceLicenseDto drivceLicenseDto) {

        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token format");
        }

        try {
            String token = accessToken.substring(7);
            String id = jwtTokenProvider.getUsernameFromToken(token);

            if (id != null) {
                userService.updateInformation(id, drivceLicenseDto);
                return ResponseEntity.status(HttpStatus.OK).body("License and address updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while updating license and address");
        }
    }

    @PutMapping("/api/v1/user/password/update")
    public ResponseEntity<?> updatePassword(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody PasswordUpdateDto passwordUpdateDto) {

        // 토큰이 존재하지 않거나 잘못된 형식일 경우
        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token format");
        }

        try {
            // 토큰에서 사용자 ID 추출
            String token = accessToken.substring(7);
            String memberId = jwtTokenProvider.getUsernameFromToken(token); // memberId로 변경
            System.out.println("Extracted memberId from token: " + memberId); // 추가된 로그

            // 사용자 ID가 유효한 경우 비밀번호 업데이트 처리
            if (memberId != null) {
                // 비밀번호 변경 처리
                System.out.println("Attempting to update password for memberId: " + memberId); // 추가된 로그
                boolean isUpdated = userService.updatePassword(memberId, passwordUpdateDto.getCurrentPassword(), passwordUpdateDto.getNewPassword());

                // 비밀번호 변경 성공 여부에 따른 응답
                if (isUpdated) {
                    System.out.println("Password updated successfully for memberId: " + memberId); // 추가된 로그
                    return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
                } else {
                    System.out.println("Current password is incorrect for memberId: " + memberId); // 추가된 로그
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect");
                }
            } else {
                System.out.println("Invalid user: " + memberId); // 추가된 로그
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 발생 시 에러 메시지 출력
            System.out.println("Error occurred while updating password: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while updating password");
        }
    }
}