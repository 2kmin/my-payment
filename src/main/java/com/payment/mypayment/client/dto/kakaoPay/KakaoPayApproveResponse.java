package com.payment.mypayment.client.dto.kakaoPay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoPayApproveResponse {

    private String aid;
    private String tid;
    private String cid;
    private String sid;
    private String partnerOrderId;
    private String partnerUserId;
    private String paymentMethodType;
    private Amount amount;
    private CardInfo cardInfo;
    private String itemName;
    private String itemCode;
    private int quantity;
    private Date createdAt;
    private String approvedAt;
    private String payload;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Amount {
        private int total;
        private int taxFree;
        private int vat;
        private int point;
        private int discount;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CardInfo {
        private String purchaseCorp;
        private String purchaseCorpCode;
        private String issuerCorp;
        private String issuerCorpCode;
        private String kakaopayPurchaseCorp;
        private String kakaopayPurchaseCorpCode;
        private String kakaopayIssuerCorp;
        private String kakaopayIssuerCorpCode;
        private String bin;
        private String cardType;
        private String installMonth;
        private String approvedId;
        private String cardMid;
        private String interestFreeInstall;
        private String cardItemCode;
    }
}
