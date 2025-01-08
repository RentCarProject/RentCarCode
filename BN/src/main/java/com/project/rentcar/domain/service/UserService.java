package com.project.rentcar.domain.service;

import com.project.rentcar.domain.dto.DrivceLicenseDto;
import com.project.rentcar.domain.dto.UserRequestDto;
import com.project.rentcar.domain.dto.UserResponseDto;
import com.project.rentcar.domain.entity.User;
import com.project.rentcar.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /** User 조회 */
    @Transactional
    public UserResponseDto findById(String memberId) {
        User user = this.userRepository.findByMemberId(memberId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. user_id = " + memberId));
        return new UserResponseDto(user);
    }

    /** User 수정 */
    @Transactional
    public void update(String memberId, UserRequestDto requestDto) {
        User user = this.userRepository.findByMemberId(memberId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. user_id = " + memberId));
        if (requestDto.getMemberPw() != null) {
            String encodedPassword = passwordEncoder.encode(requestDto.getMemberPw());
            user.setMemberPw(encodedPassword); // 암호화된 비밀번호 저장
        }
        user.update(requestDto);
    }

    /** User 삭제 */
    @Transactional
    public void delete(String memberId) {
        User user = this.userRepository.findByMemberId(memberId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. user_id = " + memberId));
        this.userRepository.delete(user);
    }

    @Transactional
    public void updateInformation(String memberId, DrivceLicenseDto dto) {
        User user = userRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 운전면허 정보 업데이트
        user.setLicenseType(dto.getLicenseType());
        user.setLicenseNumber(dto.getLicenseNumber());
        user.setLicenseIssueDate(dto.getLicenseIssueDate());
        user.setLicenseExpiryDate(dto.getLicenseExpiryDate());

        // 주소 정보 업데이트
        user.setPostcode(dto.getPostcode());
        user.setRoadAddress(dto.getRoadAddress());
        user.setDetailedAddress(dto.getDetailedAddress());

        userRepository.save(user);
    }

    @Transactional
    public boolean updatePassword(String memberId, String currentPassword, String newPassword) {
        // 사용자의 ID로 User 객체 조회
        Optional<User> userOptional = userRepository.findById(memberId);

        // 사용자 존재 확인
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // 현재 비밀번호 확인 (passwordEncoder 사용)
            if (!passwordEncoder.matches(currentPassword, user.getMemberPw())) {
                return false; // 현재 비밀번호가 일치하지 않으면 false 반환
            }

            // 새로운 비밀번호 암호화
            String encodedNewPassword = passwordEncoder.encode(newPassword);

            // 비밀번호 설정
            user.setMemberPw(encodedNewPassword);

            // 사용자 비밀번호 업데이트
            userRepository.save(user);
            return true; // 비밀번호 변경 성공
        } else {
            return false; // 사용자 존재하지 않으면 false 반환
        }

    }
}