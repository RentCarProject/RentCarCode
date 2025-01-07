package com.project.rentcar.domain.dto;

import com.project.rentcar.domain.entity.Review;
import com.project.rentcar.domain.entity.User;
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
    private byte[] image;
    private String memberId;

    public static Review dtoToEntity(ReviewDto reviewDto){
        return Review.builder()
                .reviewNumber(reviewDto.getReviewNumber())
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .rating(reviewDto.getRating())
                .date(reviewDto.getDate())
                .image(reviewDto.getImage())
                .memberId(reviewDto.getMemberId())
                .build();
    }
    public static ReviewDto entityToDto(Review review){
        return ReviewDto.builder()
                .reviewNumber(review.getReviewNumber())
                .title(review.getTitle())
                .content(review.getContent())
                .rating(review.getRating())
                .date(review.getDate())
                .image(review.getImage())
                .memberId(review.getMemberId())
                .build();
    }
}
