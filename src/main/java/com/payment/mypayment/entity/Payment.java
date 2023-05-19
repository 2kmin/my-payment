package com.payment.mypayment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Payment {

    @Id
    private String paymentId;
    private String orderNo;
    private String pgTransactionId;
    private String pgMatchedId;
    private String pgId;
    private String transactionType;
    private String memberNo;
    private int transactionAmount;
    private int productCount;
    private int productAmount;
    private int discountAmount;
    private int taxAmount;
    private int taxFreeAmount;
    private int taxSupplyAmount;
    private int vatAmount;
    private int pgDiscountAmount;
    private LocalDateTime approvedAt;
    private String platform;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
