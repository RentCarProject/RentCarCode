package com.project.rentcar.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "car")  // 테이블 이름 명시
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carId")
    private Long carId;

    @NotBlank(message = "차량 이름은 필수 입력 항목입니다.")
    @Size(max = 255, message = "차량 이름은 최대 255자까지 가능합니다.")
    @Column(name = "carName", nullable = false, length = 255)
    private String carName;

    @NotNull(message = "가격은 필수 입력 항목입니다.")
    @Digits(integer = 8, fraction = 2, message = "가격은 최대 8자리 정수와 소수점 2자리까지 가능합니다.")
    @DecimalMin(value = "0.0", inclusive = false, message = "가격은 0보다 커야 합니다.")
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @NotBlank(message = "연료 타입은 필수 입력 항목입니다.")
    @Size(max = 255, message = "연료 타입은 최대 255자까지 가능합니다.")
    @Column(name = "fuel", length = 255)
    private String fuel;

    @Min(value = 1, message = "승차 인원은 최소 1명 이상이어야 합니다.")
    @Max(value = 9, message = "승차 인원은 최대 9명까지 가능합니다.")
    @Column(name = "seating_capacity")
    private int seatingCapacity;

    @NotBlank(message = "연식은 필수 입력 항목입니다.")
    @Column(name = "year", length = 5)
    private String year;


    @Column(name = "driver_age_requirement", length = 255)
    private String driverAgeRequirement;


    @Column(name = "driving_experience", length = 255)
    private String drivingExperience;


    @Column(name = "manufacturer")
    private String manufacturer;

    @Size(max = 255, message = "옵션1은 최대 255자까지 가능합니다.")
    @Column(name = "option1")
    private String option1;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "carLogo")
    private String carLogo;
}
