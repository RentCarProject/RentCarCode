package com.project.rentcar.domain.repository;

import com.project.rentcar.domain.dto.ReviewDto;
import com.project.rentcar.domain.entity.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Review r WHERE r.reviewNumber = :reviewNumber")
    void deleteReivew(@Param("reviewNumber") Long reviewNumber);

    @Transactional
    @Modifying
    @Query("UPDATE Review r SET r.title = :title, r.content = :content, r.rating = :rating, r.date = :date WHERE r.reviewNumber = :reviewNumber")
    void updateReview(@Param("title") String title,
                      @Param("content") String content,
                      @Param("rating") int rating,
                      @Param("date") LocalDate date,
                      @Param("reviewNumber") Long reviewNumber
                      );

    List<Review> findByMemberId(String memberId);

}
