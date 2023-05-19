package com.payment.mypayment.repository;

import com.payment.mypayment.controller.payment.dto.approve.ApproveResponseBody;
import com.payment.mypayment.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    List<PaymentMethod> findByPaymentId(String paymentId);
}
