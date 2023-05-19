package com.payment.mypayment.controller.payment.dto.approve;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.payment.mypayment.common.type.ResponseType;
import com.payment.mypayment.controller.common.dto.Meta;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApproveResponse {

    private Meta meta;

    private ApproveResponseBody body;

    public static ApproveResponse ofSuccess(ApproveResponseBody body) {
        return ApproveResponse.builder()
                .meta(Meta.ofSuccess())
                .body(body)
                .build();
    }

    public static ApproveResponse ofFail(ResponseType responseType, String detailMessage) {
        return ApproveResponse.builder()
                .meta(Meta.ofFail(responseType, detailMessage))
                .build();
    }

    public static ApproveResponse ofFail(ResponseType responseType) {
        return ApproveResponse.builder()
                .meta(Meta.ofFail(responseType))
                .build();
    }

}
