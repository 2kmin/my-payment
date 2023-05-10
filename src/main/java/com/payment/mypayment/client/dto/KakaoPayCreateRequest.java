package com.payment.mypayment.client.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import feign.Param;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoPayCreateRequest {

    private String cid;

    @feign.form.FormProperty("partner_order_id")
    private String partnerOrderId;

    @feign.form.FormProperty("partner_user_id")
    private String partnerUserId;

    @feign.form.FormProperty("item_name")
    private String itemName;

    private int quantity;

    @feign.form.FormProperty("total_amount")
    private int totalAmount;

    @feign.form.FormProperty("tax_free_amount")
    private int taxFreeAmount;

    @feign.form.FormProperty("vat_amount")
    private int vatAmount;

    @feign.form.FormProperty("approval_url")
    private String approvalUrl;

    @feign.form.FormProperty("cancel_url")
    private String cancelUrl;

    @feign.form.FormProperty("fail_url")
    private String failUrl;
}
