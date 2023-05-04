package com.payment.mypayment.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PgId {

    KAKAO_PAY("kakao-pay"),
    NAVER_PAY("naver-pay");

    private final String pgId;
}
