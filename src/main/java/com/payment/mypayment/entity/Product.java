package com.payment.mypayment.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Product extends DefaultEntity {

    private String paymentId;
    private String orderNo;
    private String productName;
    private String productCode;
    private String taxType;
    private int productCount;
    private int productAmount;
    private int discountAmount;
    private int transactionAmount;
    private int taxAmount;
    private int taxFreeAmount;
    private int taxSupplyAmount;
    private int vatAmount;

}
