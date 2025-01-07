package com.project.rentcar.domain.service;


import com.project.rentcar.domain.dto.ReviewDto;
import com.project.rentcar.domain.entity.Review;
import com.project.rentcar.domain.entity.User;
import com.project.rentcar.domain.repository.ReviewRepository;
import com.project.rentcar.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewServiceImpl {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    public void addReview(ReviewDto reviewDto) {
        User user = userRepository.findByMemberId(reviewDto.getMemberId())
                .orElseThrow(() -> new RuntimeException("memberId not found"));

        Review review = Review.builder()
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .date(LocalDate.now())
                .rating(reviewDto.getRating())
                .memberId(reviewDto.getMemberId())
                .image(reviewDto.getImage())
                .build();
        reviewRepository.save(review);
    }

    public Review ReviewFindIdList(Long reviewNumber){
        return reviewRepository.findById(reviewNumber)
                .orElseThrow();
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


}
