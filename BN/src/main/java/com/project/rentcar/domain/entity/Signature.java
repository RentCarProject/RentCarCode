package com.project.rentcar.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Signature {

    @Id
    @Column(name="signKey")
    private byte[] keyBytes;
    @Column(name="createAt")
    private LocalDate createAt;
}