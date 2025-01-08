package com.project.rentcar.domain.service;

import com.project.rentcar.domain.entity.Payments;
import com.project.rentcar.domain.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    // 결제 정보 저장
    public Payments savePayment(Payments payment) {
        // 결제 정보 저장
        return paymentsRepository.save(payment);
    }
}
