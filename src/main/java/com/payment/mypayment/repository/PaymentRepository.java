package com.payment.mypayment.repository;

import com.payment.mypayment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    Optional<Payment> findByPaymentId(String paymentId);

    Optional<Payment> findByOrderNo(String orderNo);
}
