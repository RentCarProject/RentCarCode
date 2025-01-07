package com.project.rentcar.domain.repository;

import com.project.rentcar.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {


//    @Query("SELECT u FROM User u WHERE u.memberId = :memberId")
//    Optional<User> findByUsername(@Param("memberId") String memberId); //회원이름으로 rent 할 때 사용

    // 'memberId'는 엔티티의 Java 필드명입니다.
    Optional<User> findByMemberId(String memberId);  // 필드명 'memberId'로 작성

    // 'memberEmail'은 엔티티의 Java 필드명입니다.
    Optional<User> findByMemberEmail(String memberEmail);  // 필드명 'memberEmail'로 작성



}
