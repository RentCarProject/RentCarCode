package com.project.rentcar.domain.service;

import com.project.rentcar.domain.entity.TermsAndConditions;
import com.project.rentcar.domain.repository.TermsAndConditionsRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class TermsAndConditionsInitializer {

    private final TermsAndConditionsRepository termsAndConditionsRepository;

    @Autowired
    public TermsAndConditionsInitializer(TermsAndConditionsRepository termsAndConditionsRepository) {
        this.termsAndConditionsRepository = termsAndConditionsRepository;
    }

    // 애플리케이션 시작 시 동의 항목을 자동으로 추가하는 메서드
    @PostConstruct
    public void init() {
        // 데이터베이스에 항목이 없으면 동의 항목을 삽입
        if (termsAndConditionsRepository.count() == 0) {  // 동의 항목이 없으면 추가
            addTerms();
        }
    }

    // 동의 항목을 데이터베이스에 추가하는 메서드
    private void addTerms() {
        termsAndConditionsRepository.save(new TermsAndConditions("이용 약관", "본 이용 약관은 귀하가 차량을 예약하고 이용하는 모든 서비스에 대해 적용됩니다.", false));
        termsAndConditionsRepository.save(new TermsAndConditions("개인정보 처리방침", "당사는 고객님의 개인정보 보호를 최우선으로 합니다.", false));
        termsAndConditionsRepository.save(new TermsAndConditions("렌터카 이용 규정", "차량 대여 및 이용에 대한 규정을 안내드립니다.", false));
        termsAndConditionsRepository.save(new TermsAndConditions("보험 관련 사항", "렌터카 차량에는 기본 보험이 포함되어 있으나, 추가 보험 옵션에 대한 선택을 권장합니다.", false));
        termsAndConditionsRepository.save(new TermsAndConditions("음주 및 약물 관련", "음주나 약물을 복용한 상태에서 차량을 운전하는 것은 법적으로 금지되어 있으며...", false));
        termsAndConditionsRepository.save(new TermsAndConditions("차량 상태 확인 및 반환 규정", "차량을 대여받을 때 차량 상태를 반드시 확인하시고, 반환 시 차량 상태가 원상복구되어야 합니다.", false));
        termsAndConditionsRepository.save(new TermsAndConditions("기타 규정 및 법적 책임", "렌터카 서비스를 이용하는 동안 발생하는 모든 법적 책임은 고객이 부담하게 됩니다.", false));
    }
}
