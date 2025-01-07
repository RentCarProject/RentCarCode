package com.project.rentcar.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewNumber;

    @Column(name = "title" , nullable = false)
    private String title;

    @Column(name = "content" , nullable = false)
    private String content;

    @Column(name = "rating" , nullable = false)
    private int rating;     // 평가

    @Column(name = "date" , nullable = false)
    private LocalDate date;

    @Lob
    private byte[] image;

    @Column(name = "memberId")  // User의 memberId만 저장
    private String memberId;
}
