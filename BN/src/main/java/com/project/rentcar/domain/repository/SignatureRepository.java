package com.project.rentcar.domain.repository;

import com.project.rentcar.domain.entity.Signature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatureRepository extends JpaRepository<Signature, Byte> {
}
