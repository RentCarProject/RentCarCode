package com.project.rentcar.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrivceLicenseDto {
    private String licenseType;
    private String licenseNumber;
    private LocalDate licenseIssueDate;
    private LocalDate licenseExpiryDate;
    private String postcode;
    private String roadAddress;
    private String detailedAddress;
}
