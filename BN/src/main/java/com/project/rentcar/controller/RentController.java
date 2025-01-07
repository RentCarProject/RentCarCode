package com.project.rentcar.controller;

import com.project.rentcar.config.jwt.JwtTokenProvider;
import com.project.rentcar.domain.dto.RentDto;
import com.project.rentcar.domain.entity.Rent;
import com.project.rentcar.domain.entity.Review;
import com.project.rentcar.domain.service.RentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
public class RentController {
    @Autowired
    private RentServiceImpl rentServiceImpl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @PostMapping("/api/v1/rentals/rentcar")
    public ResponseEntity<String> postadd(@RequestHeader("Authorization") String accessToken, @RequestBody RentDto rentDto) {
        log.info("POST /rent/RentCar");

        // 토큰 검증
        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token format");
        }

        String token = accessToken.substring(7); // "Bearer " 부분을 제외한 실제 토큰만 추출
        String memberId = jwtTokenProvider.getUsernameFromToken(token); // 토큰에서 사용자 정보 추출

        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        // 토큰에서 추출한 memberId를 RentDto에 설정
        rentDto.setMemberId(memberId);  // RentDto에 memberId 추가

        // 차량 렌탈 처리
        rentServiceImpl.rentcar(rentDto);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }



    @PutMapping("/api/v1/rentals/extend/{rentId}")
    public ResponseEntity<String> postday(@RequestBody RentDto rentDto){
        Long rentId = rentDto.getRentId();
        LocalDate returnDate = rentDto.getReturnDate();
        log.info("POST /rent/RentDayDelay");
        log.info("POST /rent/RentDayDelay - rentId: {}, returnDate: {}", rentId, returnDate);
        boolean result = rentServiceImpl.updateDay(rentId, returnDate);
        if (result) {
            return new ResponseEntity<>("success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("failure", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/v1/rentals/end/{rentId}")
    public ResponseEntity<String> postReturn(@RequestBody RentDto rentDto){
        Long rentId = rentDto.getRentId();
        log.info("POST /rent/carReturn");
        rentServiceImpl.deleteCar(rentId);
        return new ResponseEntity<String>("success",HttpStatus.OK);
    }

    @GetMapping("/api/v1/rental/carList")
    public ResponseEntity<List<RentDto>> getlist(){
        log.info("GET /rent/carList");
        List<RentDto> rentList = rentServiceImpl.rentList();
        return new ResponseEntity<>(rentList,HttpStatus.OK);
    }


}
