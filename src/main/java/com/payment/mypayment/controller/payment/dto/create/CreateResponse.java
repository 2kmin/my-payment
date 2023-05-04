package com.payment.mypayment.controller.payment.dto.create;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.payment.mypayment.common.type.ResponseType;
import com.payment.mypayment.controller.common.dto.Meta;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateResponse {

    private Meta meta;

    private CreateResponseBody body;

    public static CreateResponse ofSuccess(CreateResponseBody body) {
        return CreateResponse.builder()
                .meta(Meta.ofSuccess())
                .build();
    }

    public static CreateResponse ofFail(ResponseType responseType) {
        return CreateResponse.builder()
                .meta(Meta.ofFail(responseType))
                .build();
    }

    public static CreateResponse ofFail(ResponseType responseType, String detailMessage) {
        return CreateResponse.builder()
                .meta(Meta.ofFail(responseType))
                .build();
    }

    public static CreateResponse ofPgFail(String pgCode, String pgMessage) {
        return CreateResponse.builder()
                .meta(Meta.ofPgFail(pgCode, pgMessage))
                .build();
    }
}