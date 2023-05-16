package com.payment.mypayment.controller.payment.dto.create;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateResponseBody {

    private String orderNo;

    private String paymentId;

    private String tid;

    private String redirectPcUrl;

    private String redirectAppUrl;

    private String redirectMobileUrl;

    private AppScheme appScheme;

    @Data
    @Builder
    public static class AppScheme {
        private String aos;
        private String ios;
    }

}
