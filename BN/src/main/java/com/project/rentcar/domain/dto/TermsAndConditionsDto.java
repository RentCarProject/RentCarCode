package com.project.rentcar.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TermsAndConditionsDto {

    private Long id;
    private String termName;
    private String description;
    private boolean isAgreed;


}
