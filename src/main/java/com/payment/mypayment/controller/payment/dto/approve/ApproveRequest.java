package com.payment.mypayment.controller.payment.dto.approve;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApproveRequest {

    @NotBlank(message = "paymentId 누락")
    private String paymentId;

    @NotBlank(message = "pgTransactionId 누락")
    private String pgTransactionId;

    @NotBlank(message = "pgToken 누락")
    private String pgToken;
}
