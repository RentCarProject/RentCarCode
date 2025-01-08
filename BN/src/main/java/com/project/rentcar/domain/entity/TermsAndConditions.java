package com.project.rentcar.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "terms_and_conditions")
public class TermsAndConditions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "term_name")
    private String termName;

    @Column(name = "description")
    private String description;

    @Column(name = "is_agreed")
    private boolean isAgreed;

    // 기본 생성자
    public TermsAndConditions() {}

    public TermsAndConditions(String termName, String description, boolean isAgreed) {
        this.termName = termName;
        this.description = description;
        this.isAgreed = isAgreed;
    }



}
