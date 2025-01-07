package com.project.rentcar.domain.repository;

import com.project.rentcar.domain.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payments, String> {
}
