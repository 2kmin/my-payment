package com.payment.mypayment.repository;

import com.payment.mypayment.entity.PaymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRequestRepository extends JpaRepository<PaymentRequest, Long> {
    Optional<PaymentRequest> findByPaymentId(String paymentId);
}
