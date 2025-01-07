package com.project.rentcar.config.details;

import com.project.rentcar.domain.entity.User;
import com.project.rentcar.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        User user = userRepository.findByMemberId(memberId).orElseThrow(
                () -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다." + memberId));


        return new CustomUserDetails(user);
    }
}
