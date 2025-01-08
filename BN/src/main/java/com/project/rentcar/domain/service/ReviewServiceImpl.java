package com.project.rentcar.domain.service;


import com.project.rentcar.domain.dto.ReviewDto;
import com.project.rentcar.domain.entity.Review;
import com.project.rentcar.domain.entity.User;
import com.project.rentcar.domain.repository.ReviewRepository;
import com.project.rentcar.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReviewServiceImpl {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    public void addReview(ReviewDto reviewDto) {
        User user = userRepository.findByMemberId(reviewDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("memberId not found"));

        String imagePath = reviewDto.getImagePath();

        Review review = Review.builder()
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .date(LocalDate.now())
                .rating(reviewDto.getRating())
                .memberId(reviewDto.getMemberId())
                .imagePath(imagePath)
                .build();
        reviewRepository.save(review);
    }

    // 이미지 저장 메소드
    public String saveImage(MultipartFile image) {
        try {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";

            // 업로드 디렉토리 존재 여부 확인
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs(); // 디렉토리 생성
            }

            // 파일 이름 생성
            String fileName = System.currentTimeMillis() + "-" + image.getOriginalFilename();
            File targetFile = new File(uploadDir, fileName);

            // 파일 저장
            image.transferTo(targetFile);

            return "/api/v1/reviews/uploads/" + fileName;  // 저장된 파일 경로 반환
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Review ReviewFindIdList(Long reviewNumber){
        return reviewRepository.findById(reviewNumber)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }



    public boolean deleteReview(Long reviewNumber){
        reviewRepository.deleteReivew(reviewNumber);
        return true;
    }

    @Transactional
    public List<Review> reviewList(){
        return reviewRepository.findAll();
    }

    public boolean updateRv(ReviewDto reviewDto){

        LocalDate reviewdate = LocalDate.now();

        reviewRepository.updateReview(
                reviewDto.getTitle(),
                reviewDto.getContent(),
                reviewDto.getRating(),
                reviewdate,
                reviewDto.getReviewNumber()
        );
        return true;
    }

    public List<ReviewDto> getUserReviews(String memberId) {
        List<Review> reviews = reviewRepository.findByMemberId(memberId);
        return reviews.stream()
                .map(ReviewDto::entityToDto)
                .collect(Collectors.toList());
    }
}
