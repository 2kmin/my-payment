package com.payment.mypayment.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "payment_method")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PaymentMethod extends DefaultEntity{
    private String paymentId;
    private String orderNo;
    private String paymentMethod;
    private String transactionType;
    private int transactionAmount;
    private LocalDateTime approvedAt;
    private String cardCompany;
    private String cardType;
    private int installment;
    private String cardApprovedNo;
}
