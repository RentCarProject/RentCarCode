package com.project.rentcar.domain;

import com.project.rentcar.domain.entity.Car;
import org.springframework.data.jpa.domain.Specification;

public class CarSpecifications {

    // 차량 이름이 포함되는지 확인
    public static Specification<Car> hasCarNameContaining(String carName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("carName")), "%" + carName.toLowerCase() + "%");
    }

    // 가격이 범위 내에 있는지 확인
    public static Specification<Car> hasPriceBetween(Integer minPrice, Integer maxPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }

    // 연료 타입이 일치하는지 확인
    public static Specification<Car> hasFuel(String fuel) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get("fuel")), fuel.toLowerCase());
    }

    // 좌석 수가 최대인지를 확인
    public static Specification<Car> hasSeatingCapacityAtMost(Integer capacity) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("seatingCapacity"), capacity);
    }

    // 좌석 수가 최소인지를 확인
    public static Specification<Car> hasSeatingCapacityAtLeast(Integer capacity) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("seatingCapacity"), capacity);
    }

    // 제조사가 포함되는지 확인
    public static Specification<Car> hasManufacturerContaining(String manufacturer) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("manufacturer")), "%" + manufacturer.toLowerCase() + "%");
    }
}
