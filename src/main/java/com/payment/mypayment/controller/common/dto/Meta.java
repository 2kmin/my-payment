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

    private String pgFailCode;

    private String message;

    private String detailMessage;

    public static Meta ofSuccess() {
        return Meta.setMeta(ResponseType.SUCCESS);
    }

    public static Meta ofWrongParameterFail(String detailMessage){
        Meta meta = setMeta(ResponseType.WRONG_PARAMETER);
        meta.setDetailMessage(detailMessage);
        return meta;
    }

    public static Meta ofPgFail(String pgCode, String pgMessage){
        Meta meta = setMeta(ResponseType.PG_FAIL);
        meta.setPgFailCode(pgCode);
        meta.setDetailMessage(pgMessage);
        return meta;
    }

    public static Meta ofFail(ResponseType responseType){
        return Meta.setMeta(responseType);
    }

    public static Meta ofFail(ResponseType responseType, String detailMessage){
        Meta meta = Meta.setMeta(responseType);
        meta.setDetailMessage(detailMessage);
        return meta;
    }

    private static Meta setMeta(ResponseType responseType){
        return Meta.builder()
                .code(responseType.getCode())
                .message(responseType.getMessage())
                .build();
    }
}
