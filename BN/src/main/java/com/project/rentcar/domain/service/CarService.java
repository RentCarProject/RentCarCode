package com.project.rentcar.domain.service;

import com.project.rentcar.domain.CarSpecifications;
import com.project.rentcar.domain.entity.Car;
import com.project.rentcar.domain.entity.Rent;
import com.project.rentcar.domain.repository.CarRepository;
import com.project.rentcar.domain.repository.RentRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RentRepository rentRepository;

    // 차량 저장
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    // 차량 ID로 조회
    public Car getCarById(Long carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id " + carId));
    }

    // 차량 수정
    @Transactional
    public Car updateCar(Long carId, Car updatedCar) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id " + carId));

        car.setCarName(updatedCar.getCarName());
        car.setManufacturer(updatedCar.getManufacturer());
        car.setPrice(updatedCar.getPrice());
        car.setFuel(updatedCar.getFuel());
        car.setSeatingCapacity(updatedCar.getSeatingCapacity());
        car.setDriverAgeRequirement(updatedCar.getDriverAgeRequirement());
        car.setDrivingExperience(updatedCar.getDrivingExperience());

        return carRepository.save(car);
    }

    // 차량 삭제
    @Transactional
    public void deleteCar(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id " + carId));
        carRepository.delete(car);
    }

    // 필터링된 차량 목록 반환
    public List<Car> getFilteredCars(String carName, Integer minPrice, Integer maxPrice, String fuel, String seatingCapacity, String manufacturer) {
        Specification<Car> spec = Specification.where(null);

        if (carName != null && !carName.isEmpty()) {
            spec = spec.and(CarSpecifications.hasCarNameContaining(carName));
        }

        if (fuel != null && !fuel.isEmpty()) {
            spec = spec.and(CarSpecifications.hasFuel(fuel));
        }

        if (seatingCapacity != null && !seatingCapacity.isEmpty()) {
            switch (seatingCapacity) {
                case "5":
                    spec = spec.and(CarSpecifications.hasSeatingCapacityAtMost(5));
                    break;
                case "7":
                    spec = spec.and(CarSpecifications.hasSeatingCapacityAtLeast(7));
                    break;
                case "9":
                    spec = spec.and(CarSpecifications.hasSeatingCapacityAtLeast(9));
                    break;
                default:
                    break;
            }
        }

        if (manufacturer != null && !manufacturer.isEmpty()) {
            spec = spec.and(CarSpecifications.hasManufacturerContaining(manufacturer));
        }

        return carRepository.findAll(spec);
    }

    // 특정 날짜 범위 내에서 예약 가능한 차량 반환
    public List<Car> getAvailableCars(LocalDate startDate, LocalDate endDate) {
        // 예약되지 않은 차량 조회
        return carRepository.findAvailableCars(startDate, endDate);
    }
}
