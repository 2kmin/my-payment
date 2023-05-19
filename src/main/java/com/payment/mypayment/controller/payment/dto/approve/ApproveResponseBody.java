package com.payment.mypayment.controller.payment.dto.approve;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApproveResponseBody {

    private String orderNo;

    private String paymentId;

    private int amount;

    private List<PaymentMethod> paymentMethod;

    private String approveAt;

    @Data
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class PaymentMethod {
        private String method; // CARD, BANK, POINT, DISCOUNT
        private int amount;
    }

}
