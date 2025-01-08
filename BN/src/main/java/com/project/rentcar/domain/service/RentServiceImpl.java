package com.project.rentcar.domain.service;

import com.project.rentcar.domain.dto.RentDto;
import com.project.rentcar.domain.entity.Car;
import com.project.rentcar.domain.entity.Rent;
import com.project.rentcar.domain.entity.User;
import com.project.rentcar.domain.repository.CarRepository;
import com.project.rentcar.domain.repository.RentRepository;
import com.project.rentcar.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RentServiceImpl {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;


    public void rentcar(RentDto rentDto) {
        // username을 통해 User 객체 조회
        User user = userRepository.findByMemberId(rentDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("username not found"));

        // Carname를 통해 Car 객체 조회
        Car car = carRepository.findByCarName(rentDto.getCarname())
                .orElseThrow(() -> new RuntimeException("carname not found"));

        // 대여 객체 생성 및 저장
        Rent rent = Rent.builder()
                .user(user)
                .car(car)
                .rentDate(LocalDate.now())
                .returnDate(LocalDate.now().plusDays(1))
                .build();

        rentRepository.save(rent);
    }
    public boolean updateDay(Long rentId, LocalDate returnDate) {
        Rent rent = rentRepository.findById(rentId)
                .orElseThrow(() -> new EntityNotFoundException("rent not found"));

        // 선택된 반납일로 수정
        int updatedRows = rentRepository.updateReturnDate(returnDate, rent.getRentId());

        // 업데이트된 행의 수에 따라 true 또는 false 반환
        return updatedRows > 0; // 업데이트된 행이 있으면 true 반환
    }

    public boolean deleteCar(Long rentId){

        rentRepository.deleteCar(rentId);
        return true;
    }

//    public List<RentDto> rentList() {
//        List<Rent> rentList = rentRepository.findAll();
//        log.info("렌트 목록: {}", rentList);
//
//        // Rent 객체를 RentDto로 변환
//        List<RentDto> rentDtoList = rentList.stream()
//                .map(RentDto::EntityToDto)
//                .collect(Collectors.toList());
//
//        return rentDtoList;
//    }
    public List<RentDto> rentListByMember(String memberId) {
    // Rent 객체 목록을 가져옵니다.
        List<Rent> rentList = rentRepository.findByUserAndCarWithPrice(memberId);

        // Rent 객체를 RentDto로 변환
        List<RentDto> rentDtoList = rentList.stream().map(rent -> {
            return RentDto.builder()
                    .rentId(rent.getRentId())  // rentId
                    .memberId(rent.getUser().getMemberId())  // memberId
                    .carname(rent.getCar().getCarName())  // carname
                    .price(rent.getCar().getPrice())  // price
                    .rentDate(rent.getRentDate())  // rentDate
                    .returnDate(rent.getReturnDate())  // returnDate
                    .build();
        }).collect(Collectors.toList());

        return rentDtoList;
    }




}

