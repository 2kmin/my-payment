package com.payment.mypayment.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseType {

    SUCCESS("0000", "성공"),
    WRONG_PARAMETER("9000", "파라미터를 확인해주세요"),

    PG_FAIL("9001", "PG 실패")
    ;

    private final String code;
    private final String message;
}
