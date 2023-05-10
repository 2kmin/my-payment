package com.payment.mypayment.service.create;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.mypayment.client.KakaoPayClient;
import com.payment.mypayment.client.dto.ClientResponse;
import com.payment.mypayment.client.dto.KakaoPayCreateRequest;
import com.payment.mypayment.client.dto.KakaoPayCreateResponse;
import com.payment.mypayment.common.type.ResponseType;
import com.payment.mypayment.controller.common.dto.AmountInfo;
import com.payment.mypayment.controller.payment.dto.create.CreateRequest;
import com.payment.mypayment.controller.payment.dto.create.CreateResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoPayCreateService {

    private final KakaoPayClient kakaoPayClient;
    private final ObjectMapper objectMapper;

    @Value("${key.kakao.cid}")
    private String CID;

    public CreateResponse create(String paymentId, CreateRequest createRequest, AmountInfo amountInfo) {

        ClientResponse response = getPgResponse(createRequest, amountInfo);
        log.info(">>> 카카오페이 결제 생성 결과 : " + response);


        return null;
    }

    public ClientResponse getPgResponse(CreateRequest createRequest, AmountInfo amountInfo) {

        ClientResponse clientResponse = new ClientResponse();
        ResponseEntity<String> pgResponse = null;

        KakaoPayCreateRequest kakaoPayCreateRequest = KakaoPayCreateRequest.builder()
                .cid(CID)
                .partnerOrderId(Long.toString(createRequest.getOrderNo()))
                .partnerUserId(createRequest.getMemberNo())
                .itemName(createRequest.getProductList().get(0).getProductName())
                .quantity(amountInfo.getTotalCount())
                .totalAmount(amountInfo.getTransactionAmount())
                .taxFreeAmount(amountInfo.getTotalTaxFreeAmount())
                .vatAmount(amountInfo.getTotalVatAmount())
                .approvalUrl(createRequest.getApprovalUrl())
                .cancelUrl(createRequest.getCancelUrl())
                .failUrl(createRequest.getFailUrl())
                .build();

        try{
            pgResponse = kakaoPayClient.requestCreate(kakaoPayCreateRequest);
            KakaoPayCreateResponse kakaoPayCreateResponse = objectMapper.readValue(pgResponse.getBody(), KakaoPayCreateResponse.class);

            clientResponse = ClientResponse.builder()
                    .isSuccess(true)
                    .pgResponse(kakaoPayCreateResponse)
                    .build();

        }catch (FeignException e){
            log.error("[KakaoPayCreateService.getPgResponse] 카카오페이 결제 생성 실패 FeignException - " + e);
            try{
                Map<String, Object> map = objectMapper.readValue(e.contentUTF8(), Map.class);

                String code = ObjectUtils.isEmpty(map.get("code")) ? String.valueOf(e.status()) : String.valueOf(map.get("code"));
                String message = ObjectUtils.isEmpty(map.get("msg")) ? ResponseType.COMMUNICATION_ERROR.getMessage() : (String) map.get("msg");

                clientResponse = ClientResponse.builder()
                        .isSuccess(false)
                        .pgCode(code)
                        .pgMessage(message)
                        .build();

            }catch (JsonProcessingException ee){
                log.error("[KakaoPayCreateService.getPgResponse] JsonProcessingException - " + e);
                clientResponse = ClientResponse.builder()
                        .isSuccess(false)
                        .pgCode(ResponseType.PROCESSING_ERROR.getCode())
                        .pgMessage(ResponseType.PROCESSING_ERROR.getMessage())
                        .build();
            }
        }catch (Exception e){
            log.error("[KakaoPayCreateService.getPgResponse] Exception - " + e);
            clientResponse = ClientResponse.builder()
                    .isSuccess(false)
                    .pgCode(ResponseType.PROCESSING_ERROR.getCode())
                    .pgMessage(ResponseType.PROCESSING_ERROR.getMessage())
                    .build();
        }

        return clientResponse;
    }
}
