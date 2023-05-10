package com.payment.mypayment.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PgId {

    KAKAO_PAY("kakao-pay", "카카오 페이"),
    NAVER_PAY("naver-pay", "네이버 페이");

    private final String pgId;
    private final String korName;
}
