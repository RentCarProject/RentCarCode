package com.project.rentcar.domain.repository;

import com.project.rentcar.domain.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {

    // 기존 메서드들 유지
    List<Car> findByCarNameContaining(String carName);
    List<Car> findByManufacturerContaining(String manufacturer);
    List<Car> findByCarNameContainingAndPriceBetween(String carName, Integer minPrice, Integer maxPrice);
    List<Car> findByPriceBetween(Integer minPrice, Integer maxPrice);
    List<Car> findAll();

    Optional<Car> findByCarName(String carName);

    // 새로 추가된 날짜 범위에 따라 예약 가능한 차량 조회
    @Query("SELECT c FROM Car c WHERE NOT EXISTS (" +
            "SELECT 1 FROM Rent r WHERE r.car = c " +
            "AND (r.rentDate <= :endDate AND r.returnDate >= :startDate))")
    List<Car> findAvailableCars(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
