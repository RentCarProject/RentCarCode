package com.project.rentcar.domain.dto;

import com.project.rentcar.domain.entity.Car;
import com.project.rentcar.domain.entity.Rent;
import com.project.rentcar.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentDto {
    private Long rentId;
    private String memberId;
    private String carname;
    private BigDecimal price;
    private LocalDate rentDate;
    private LocalDate returnDate;

    public static Rent dtoToEntity(RentDto rentDto){
        Car car = Car.builder()
                .carName(rentDto.getCarname())
                .price(rentDto.getPrice())
                .build();

        return Rent.builder()
                .rentId(rentDto.getRentId())
                .user(User.builder().memberId(rentDto.getMemberId()).build())
                .car(car)  // 하나의 Car 객체를 사용
                .rentDate(rentDto.getRentDate())
                .returnDate(rentDto.getReturnDate())
                .build();
    }
    public static RentDto EntityToDto(Rent rent){
        return RentDto.builder()
                .rentId(rent.getRentId())
                .memberId(rent.getUser().getMemberId())
                .carname(rent.getCar().getCarName())
                .price(rent.getCar().getPrice())
                .rentDate(rent.getRentDate())
                .returnDate(rent.getReturnDate())
                .build();
    }

}
