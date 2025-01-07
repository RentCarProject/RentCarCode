package com.project.rentcar.domain.service;

import com.project.rentcar.domain.dto.UserRequestDto;
import com.project.rentcar.domain.dto.UserResponseDto;
import com.project.rentcar.domain.entity.User;
import com.project.rentcar.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        User user = this.userRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다. user_id = " + memberId));
        this.userRepository.delete(user);
    }
}