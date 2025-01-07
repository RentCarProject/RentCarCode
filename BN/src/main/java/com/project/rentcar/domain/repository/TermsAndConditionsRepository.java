package com.project.rentcar.domain.repository;

import com.project.rentcar.domain.entity.TermsAndConditions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsAndConditionsRepository extends JpaRepository<TermsAndConditions, Long> {
}
