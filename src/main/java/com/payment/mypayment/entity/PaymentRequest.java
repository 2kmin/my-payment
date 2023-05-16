package com.payment.mypayment.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "payment_request")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PaymentRequest extends DefaultEntity{

    private String paymentId;
    private String orderNo;
    private String memberNo;
    private int transactionAmount;
    private int totalCount;
    private int totalProductAmount;
    private int totalDiscountAmount;
    private int totalTaxAmount;
    private int totalTaxFreeAmount;
    private int totalTaxSupplyAmount;
    private int totalVatAmount;

}
