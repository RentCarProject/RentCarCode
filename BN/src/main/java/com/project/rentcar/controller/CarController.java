package com.project.rentcar.controller;

import com.project.rentcar.domain.entity.Car;
import com.project.rentcar.domain.service.CarService;
import com.project.rentcar.domain.service.RentServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
public class CarController {

    private final CarService carService;
    private final RentServiceImpl rentService;

    @Autowired
    public CarController(CarService carService, RentServiceImpl rentService) {
        this.carService = carService;
        this.rentService = rentService;
    }

    // 차량 상세 조회
    @GetMapping("api/cars/view/{carId}")
    public ResponseEntity<Car> viewCar(@PathVariable("carId") Long carId) {
        try {
            Car car = carService.getCarById(carId);
            return ResponseEntity.ok(car);
        } catch (EntityNotFoundException e) {
            log.error("Car not found with ID: {}", carId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 특정 날짜에 렌트 가능한 차량 목록 반환
    @GetMapping("api/cars/available")
    public ResponseEntity<List<Car>> getAvailableCars(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            log.info("Fetching available cars from {} to {}", startDate, endDate);

            List<Car> availableCars = carService.getAvailableCars(startDate, endDate);

            if (availableCars.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(availableCars);
        } catch (Exception e) {
            log.error("Error fetching available cars: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 차량 목록 조회 (fuel 필터링)
    @GetMapping("api/cars/list")
    public ResponseEntity<List<Car>> listCars(
            @RequestParam(value = "fuel", required = false) String fuel) {
        List<Car> cars = carService.getFilteredCars(null, null, null, fuel, null, null);

        log.info("Filtered cars: {}", cars);

        if (cars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(cars);
    }

    // 차량 생성
    @PostMapping("api/cars/create")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        try {
            Car savedCar = carService.saveCar(car);
            log.info("POST /cars/create - Created car: {}", savedCar);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCar);
        } catch (Exception e) {
            log.error("Error creating car: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 차량 수정
    @PutMapping("api/cars/update/{carId}")
    public ResponseEntity<Car> updateCar(@PathVariable("carId") Long carId, @RequestBody Car car) {
        try {
            Car updatedCar = carService.updateCar(carId, car);
            log.info("PUT /cars/update - Updated car: {}", updatedCar);
            return ResponseEntity.ok(updatedCar);
        } catch (EntityNotFoundException e) {
            log.error("Car not found with ID: {}", carId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error updating car: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 차량 삭제
    @DeleteMapping("api/cars/delete/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable("carId") Long carId) {
        try {
            carService.deleteCar(carId);
            log.info("Successfully deleted car with ID: {}", carId);
            return ResponseEntity.ok("차량 삭제 성공!");
        } catch (EntityNotFoundException e) {
            log.error("Car not found with ID: {}", carId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("차량을 찾을 수 없습니다.");
        } catch (Exception e) {
            log.error("Error deleting car: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("차량 삭제 중 오류 발생");
        }
    }
}