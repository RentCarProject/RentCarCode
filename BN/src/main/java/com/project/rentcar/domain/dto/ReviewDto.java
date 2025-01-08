package com.project.rentcar.domain.dto;

import com.project.rentcar.domain.entity.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private Long reviewNumber;
    private String title;
    private String content;
    private int rating;
    private LocalDate date;
    private String memberId;
    private String imagePath;  // 이미지 경로 추가

    // 추가된 생성자
    public ReviewDto(String title, String content, int rating, String memberId, String imagePath) {
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.memberId = memberId;
        this.imagePath = imagePath;
        this.date = LocalDate.now();  // 현재 날짜 자동 설정
    }

    public static Review dtoToEntity(ReviewDto reviewDto){
        return Review.builder()
                .reviewNumber(reviewDto.getReviewNumber())
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .rating(reviewDto.getRating())
                .date(LocalDate.now())
                .memberId(reviewDto.getMemberId())
                .imagePath(reviewDto.getImagePath())  // 이미지 경로 설정
                .build();
    }

    public static ReviewDto entityToDto(Review review){
        return ReviewDto.builder()
                .reviewNumber(review.getReviewNumber())
                .title(review.getTitle())
                .content(review.getContent())
                .rating(review.getRating())
                .date(review.getDate())
                .memberId(review.getMemberId())
                .imagePath(review.getImagePath())  // 이미지 경로 설정
                .build();
    }
}



