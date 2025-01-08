package com.project.rentcar.domain.repository;

import com.project.rentcar.domain.entity.Auth;
import com.project.rentcar.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {
    boolean existsByUser(User user);

    Optional<Auth> findByRefreshToken(String refreshToken);

    Optional<Auth> findByUser(User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM Auth a WHERE a.accessToken = :token ")
    void deleteByAccessTokenOrRefreshToken(@Param("token") String token);
}
