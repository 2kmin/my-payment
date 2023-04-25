package com.payment.mypayment.controller.common.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.payment.mypayment.common.type.ResponseType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Meta {

    private String code;

    private String message;

    private String detailMessage;

    public static Meta ofSuccess() {
        return Meta.ofFail(ResponseType.SUCCESS);
    }

    public static Meta ofWrongParameterFail(String detailMessage){
        Meta meta = ofFail(ResponseType.WRONG_PARAMETER);
        meta.setDetailMessage(detailMessage);
        return meta;
    }

    public static Meta ofPgFail(String pgFailMessage){
        Meta meta = ofFail(ResponseType.PG_FAIL);
        meta.setDetailMessage("[PG Message] "+pgFailMessage);
        return meta;
    }

    public static Meta ofFail(ResponseType responseType){
        return Meta.builder()
                .code(responseType.getCode())
                .message(responseType.getMessage())
                .build();
    }
}
