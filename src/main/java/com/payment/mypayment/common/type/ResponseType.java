package com.payment.mypayment.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseType {

    SUCCESS("0000", "성공"),
    WRONG_PARAMETER("9000", "파라미터를 확인해주세요"),
    WRONG_PG_ID("9001", "부정확한 PG ID"),

    PG_FAIL("9001", "PG 실패")
    ;

    private final String code;
    private final String message;
}
