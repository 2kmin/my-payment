package com.payment.mypayment.controller.payment.dto.create;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.payment.mypayment.controller.common.dto.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateRequest {

    @NotBlank(message = "주문번호 누락")
    private String orderNo;

    @NotBlank(message = "memberNo 누락")
    private String memberNo;

    @Valid
    @NotNull(message = "결제 상품 누락")
    private List<Product> productList;

    @NotBlank(message = "approvalUrl 누락")
    private String approvalUrl;

    @NotBlank(message = "cancelUrl 누락")
    private String cancelUrl;

    @NotBlank(message = "failUrl 누락")
    private String failUrl;
}