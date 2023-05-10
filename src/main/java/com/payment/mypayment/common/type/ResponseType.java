package com.payment.mypayment.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseType {

    // 성공 
    SUCCESS("0000", "성공"),
    
    // 공통 실패
    WRONG_PARAMETER("9999", "파라미터를 확인해주세요"),
    WRONG_PG_ID("9998", "부정확한 PG ID"),
    PG_FAIL("9997", "PG 실패"),
    PROCESSING_ERROR("9996", "내부 처리 오류"),
    COMMUNICATION_ERROR("9995", "PG 통신 실패")
    ;

    private final String code;
    private final String message;
}
