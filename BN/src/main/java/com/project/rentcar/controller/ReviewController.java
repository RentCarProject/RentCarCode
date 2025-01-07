package com.project.rentcar.controller;

import com.project.rentcar.config.jwt.JwtTokenProvider;
import com.project.rentcar.domain.dto.ReviewDto;
import com.project.rentcar.domain.entity.Review;
import com.project.rentcar.domain.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    @Autowired
    private ReviewServiceImpl reviewServiceImpl;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/api/v1/reviews")
    public ResponseEntity<List<Review>> reviewMain() {
        log.info("GET /reivew/reviewMain");
        List<Review> rList = reviewServiceImpl.reviewList();

        return new ResponseEntity<>(rList,HttpStatus.OK);
    }

    @GetMapping("/reviewRead")
    public void reviewRead(){
        log.info("GET /review/reviewRead");
    }

    @GetMapping("/api/v1/reviews/{reviewNumber}")
    public ResponseEntity<Review> reviewsid(@PathVariable("reviewNumber") Long reviewNumber){
        log.info("GET /reivews/{reviewNumber}",reviewNumber);
        Review review = reviewServiceImpl.ReviewFindIdList(reviewNumber);
        return ResponseEntity.ok(review);

    }

    @PostMapping("/api/v1/reviews/add")
    public ResponseEntity<String> postAddReview(@RequestHeader("Authorization") String accessToken,
                                                @RequestBody ReviewDto reviewDto) {
        log.info("POST /api/v1/reviews/add");

        // 토큰 검증
        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token format");
        }

        String token = accessToken.substring(7); // "Bearer " 부분을 제외한 실제 토큰만 추출
        String userId = jwtTokenProvider.getUsernameFromToken(token); // 토큰에서 사용자 정보 추출

        System.out.println("User ID from Token: " + userId);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        // 사용자 인증이 완료된 후 리뷰 작성
        reviewDto.setMemberId(userId);  // 리뷰에 사용자 정보를 추가 (필요한 경우)
        reviewServiceImpl.addReview(reviewDto);

        return ResponseEntity.status(HttpStatus.OK).body("리뷰작성 완료");
    }

    @PutMapping("/api/v1/reviews/update/{reviewNumber}")
    public ResponseEntity<String> updatereivew(
            @RequestHeader("Authorization") String accessToken,   // Authorization 헤더에서 토큰을 받음
            @RequestBody ReviewDto reviewDto) {

        log.info("PUT /review/updateReview");

        // 토큰 검증
        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token format");
        }

        String token = accessToken.substring(7);  // "Bearer " 부분을 제외한 실제 토큰만 추출
        String userId = jwtTokenProvider.getUsernameFromToken(token);  // 토큰에서 사용자 정보 추출

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        // 리뷰를 수정하는 사용자와 요청한 사용자가 일치하는지 확인
        Review review = reviewServiceImpl.ReviewFindIdList(reviewDto.getReviewNumber());
        if (review == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");
        }

        if (!review.getMemberId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to update this review");
        }

        // 리뷰 수정
        reviewDto.setMemberId(userId);  // 토큰에서 추출한 userId를 reviewDto에 설정
        reviewServiceImpl.updateRv(reviewDto);  // 리뷰 수정 서비스 호출

        return ResponseEntity.status(HttpStatus.OK).body("리뷰 수정 완료");
    }

    @DeleteMapping("/api/v1/reviews/delete/{reviewNumber}")
    public ResponseEntity<String> postdeleteReview(@RequestHeader("Authorization") String accessToken,
                                                   @PathVariable("reviewNumber") Long reviewNumber) {
        log.info("POST /reivew/deleteReview");

        // 토큰 검증
        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token format");
        }

        String token = accessToken.substring(7); // "Bearer " 부분을 제외한 실제 토큰만 추출
        String userId = jwtTokenProvider.getUsernameFromToken(token); // 토큰에서 사용자 정보 추출

        System.out.println("User ID from Token: " + userId);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        // 해당 리뷰를 작성한 사용자인지 확인
        Review review = reviewServiceImpl.ReviewFindIdList(reviewNumber); // 리뷰 정보 조회
        if (review == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");
        }

        if (!review.getMemberId().equals(userId)) {
            // 해당 사용자가 작성한 리뷰가 아니면 삭제할 수 없음
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to delete this review");
        }

        // 리뷰 삭제
        reviewServiceImpl.deleteReview(reviewNumber);

        return ResponseEntity.noContent().build();
    }


}
