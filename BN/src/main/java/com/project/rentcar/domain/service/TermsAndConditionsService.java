package com.project.rentcar.domain.service;

import com.project.rentcar.domain.dto.TermsAndConditionsDto;
import com.project.rentcar.domain.entity.TermsAndConditions;
import com.project.rentcar.domain.repository.TermsAndConditionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TermsAndConditionsService {

    private final TermsAndConditionsRepository termsAndConditionsRepository;

    @Autowired
    public TermsAndConditionsService(TermsAndConditionsRepository termsAndConditionsRepository) {
        this.termsAndConditionsRepository = termsAndConditionsRepository;
    }

    // 특정 동의 항목 동의 처리
    public void agreeToTerms(Long termId, boolean isAgreed) {
        TermsAndConditions term = termsAndConditionsRepository.findById(termId)
                .orElseThrow(() -> new IllegalArgumentException("Term ID " + termId + " not found in the database"));
        term.setAgreed(isAgreed);  // 동의 여부를 true로 설정
        termsAndConditionsRepository.save(term);  // 저장
    }

    // 모든 동의 항목 가져오기
    public List<TermsAndConditionsDto> getAllTermsAndConditions() {
        List<TermsAndConditions> terms = termsAndConditionsRepository.findAll();
        return terms.stream()
                .map(term -> new TermsAndConditionsDto(term.getId(), term.getTermName(), term.getDescription(), term.isAgreed()))
                .collect(Collectors.toList());
    }
}
