package com.payment.mypayment.controller.common.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AmountInfo {

    private int transactionAmount; // 결제금액

    private int totalCount; // 총 수량

    private int totalProductAmount; // 총 상품금액

    private int totalDiscountAmount; // 총 할인금액
    
    private int totalTaxAmount; // 과세 대상금액

    private int totalTaxFreeAmount; // 비과세 대상금액

    private int totalTaxSupplyAmount; // 과세 공급가 (결제금액 - 부가세)

    private int totalVatAmount; // 부가세

    private List<ProductTaxInfo> productTaxInfoList;

    @Data
    @Builder
    public static class ProductTaxInfo {

        private Product product;

        private int taxAmount; // 과세 대상금액

        private int taxFreeAmount; // 비과세 대상금액

        private int taxSupplyAmount; // 과세 공급가 (결제금액 - 부가세)

        private int vatAmount; // 부가세



    }
}
