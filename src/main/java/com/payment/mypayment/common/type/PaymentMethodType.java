package com.payment.mypayment.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethodType {
    KAKAOPAY_CARD(PgId.KAKAO_PAY, "CARD", CommonPaymentMethodType.card.name()),
    KAKAOPAY_POINT(PgId.KAKAO_PAY, "POINT", CommonPaymentMethodType.pg_point.name()),
    KAKAOPAY_MONEY(PgId.KAKAO_PAY, "MONEY", CommonPaymentMethodType.pay_money.name()),
    KAKAOPAY_PROMOTION(PgId.KAKAO_PAY, "DISCOUNT", CommonPaymentMethodType.pg_discount.name());

    private PgId pgId;
    private String code;
    private String paymentMethodTypeCode;

    public enum CommonPaymentMethodType {
        card,
        pg_point,
        pay_money,
        pg_discount
    }

    public static String getCommonPaymentMethodType(PgId pgId, String code) {
        for(PaymentMethodType paymentMethodType : values()) {
            if(pgId.equals(paymentMethodType.pgId) && code.equals(paymentMethodType.code)) {
                return paymentMethodType.getCode();
            }
        }
        throw new RuntimeException("알수 없는 결제 수단입니다 [PG ID : "+pgId+"][PG CODE : "+code+"]");
    }
}