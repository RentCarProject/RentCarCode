package com.project.rentcar.controller;

import com.project.rentcar.config.jwt.JwtTokenProvider;
import com.project.rentcar.domain.dto.ReviewDto;
import com.project.rentcar.domain.entity.Review;
import com.project.rentcar.domain.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        return new ResponseEntity<>(rList, HttpStatus.OK);
    }

    @GetMapping("/api/v1/reviews/user")
    public ResponseEntity<List<ReviewDto>> getUserReviews(@RequestHeader("Authorization") String accessToken) {
        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  // 토큰 형식이 잘못된 경우
        }

        String token = accessToken.substring(7);  // "Bearer " 부분을 제외한 실제 토큰만 추출
        String userId = jwtTokenProvider.getUsernameFromToken(token);  // JWT에서 사용자 정보 추출

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  // 토큰에서 사용자 ID를 추출할 수 없는 경우
        }

        // 사용자 인증이 완료된 후 해당 사용자의 리뷰를 가져옴
        List<ReviewDto> reviewDtos = reviewServiceImpl.getUserReviews(userId);

        return ResponseEntity.ok(reviewDtos);  // 리뷰 목록 반환
    }


    @GetMapping("/api/v1/reviews/{reviewNumber}")
    public ResponseEntity<Review> reviewsid(@PathVariable("reviewNumber") Long reviewNumber) {
        log.info("GET /reviews/{reviewNumber}", reviewNumber);
        Review review = reviewServiceImpl.ReviewFindIdList(reviewNumber);

        // 이미지 경로 수정: 로컬 경로를 웹 경로로 변환
        if (review.getImagePath() != null && !review.getImagePath().isEmpty()) {
            // 파일 경로만 반환되도록 처리
            String baseUrl = "http://localhost:8090/api/v1/reviews/uploads/";
            Path path = Paths.get(review.getImagePath());
            String fileName = path.getFileName().toString();  // 파일 이름만 추출
            review.setImagePath(baseUrl + fileName);

            // 로그 추가: 이미지 경로가 잘 설정되었는지 확인
            log.info("Converted image path for review {}: {}", reviewNumber, review.getImagePath());
        }

        return ResponseEntity.ok(review);
    }

    @GetMapping("/api/v1/reviews/uploads/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable("fileName") String fileName) {
        try {
            Path filePath = Paths.get("C:/01-07/BN/uploads").resolve(fileName).normalize();
            Resource resource = new FileSystemResource(filePath);

            if (resource.exists() || resource.isReadable()) {
                String contentType = Files.probeContentType(filePath);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IOException e) {
            log.error("Error loading image: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @PostMapping("/api/v1/reviews/add")
    public ResponseEntity<String> postAddReview(@RequestHeader("Authorization") String accessToken,
                                                @RequestParam("title") String title,
                                                @RequestParam("content") String content,
                                                @RequestParam("rating") int rating,
                                                @RequestParam(value = "image", required = false) MultipartFile image) {
        log.info("POST /api/v1/reviews/add");

        if (image != null) {
            log.info("Received image: " + image.getOriginalFilename() + ", size: " + image.getSize());
        } else {
            log.info("No image received.");
        }

        // 토큰 검증
        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token format");
        }

        String token = accessToken.substring(7); // "Bearer " 부분을 제외한 실제 토큰만 추출
        String userId = jwtTokenProvider.getUsernameFromToken(token); // 토큰에서 사용자 정보 추출

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        // 이미지 파일 처리 (이미지 저장 등 추가 필요)
        String imagePath = null;
        if (image != null && !image.isEmpty()) {
            log.info("Saving image: " + image.getOriginalFilename() + ", size: " + image.getSize());
            imagePath = reviewServiceImpl.saveImage(image);  // 서비스에서 이미지 저장 로직을 처리
        }

        // 사용자 인증이 완료된 후 리뷰 작성
        ReviewDto reviewDto = new ReviewDto(title, content, rating, userId, imagePath);
        reviewServiceImpl.addReview(reviewDto);

        return ResponseEntity.status(HttpStatus.OK).body("리뷰 작성 완료");
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
