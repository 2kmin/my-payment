package com.payment.mypayment.client.dto.kakaoPay;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoPayApproveRequest {

    private String cid;

    private String tid;

    @feign.form.FormProperty("partner_order_id")
    private String partnerOrderId;

    @feign.form.FormProperty("partner_user_id")
    private String partnerUserId;

    @feign.form.FormProperty("pg_token")
    private String pgToken;

}
