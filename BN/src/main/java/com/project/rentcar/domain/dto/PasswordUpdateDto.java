package com.project.rentcar.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordUpdateDto {
    private String currentPassword;
    private String newPassword;
}
