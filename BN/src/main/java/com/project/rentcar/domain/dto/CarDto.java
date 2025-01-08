package com.project.rentcar.domain.dto;



import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    @NotNull(message = "차량 ID를 입력하세요")
    private Long Carid;

    @NotBlank(message = "차량명을 입력하세요")
    private String carname;

    @NotBlank(message = "차량 모델을 입력하세요")
    private String model;

    @NotBlank(message = "차량 브랜드를 입력하세요")
    private String brand;

    @Min(value = 2000, message = "최소 연도는 2000년 이상")
    @Max(value = 2025, message = "최대 연도는 2025년 이하")
    @NotNull(message = "제조 연도를 입력하세요")
    private Integer year;

    @NotNull(message = "차량 가격을 입력하세요")
    @Positive(message = "차량 가격은 양수여야 합니다")
    private Double price;

    @Pattern(regexp = "^[A-Za-z0-9]{1,17}$", message = "차량 번호는 1에서 17자의 영문과 숫자만 포함해야 합니다")
    private String licensePlate;

    @Min(value = 0, message = "주행거리는 0 이상이어야 합니다")
    private Integer mileage;
}
