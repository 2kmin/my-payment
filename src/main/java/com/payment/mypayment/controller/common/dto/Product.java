package com.payment.mypayment.controller.common.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Product {

    @NotBlank(message = "상품 이름이 없습니다.")
    private String productName;

    @NotBlank(message = "상품 코드가 없습니다.")
    private String productCode;

    @NotBlank(message = "과세 유형이 없습니다.")
    @Pattern(regexp = "^(tax|tax_free)$", message = "과세유형은 tax 또는 tax_free 이어야 합니다.")
    private String taxType;

    @Positive(message = "상품 갯수는 0보다 커야합니다.")
    private int count;

    @Positive(message = "상품 금액은 0보다 커야합니다.")
    private int productAmount;

    @Positive(message = "상품 할인 금액은 0보다 커야합니다.")
    private int discountAmount;

    @Positive(message = "상품 결제 금액은 0보다 커야합니다.")
    private int transactionAmount;
}
