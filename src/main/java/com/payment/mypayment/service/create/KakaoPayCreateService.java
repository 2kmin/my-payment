package com.payment.mypayment.service.create;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.mypayment.client.KakaoPayClient;
import com.payment.mypayment.client.dto.ClientResponse;
import com.payment.mypayment.client.dto.KakaoPayCreateRequest;
import com.payment.mypayment.client.dto.KakaoPayCreateResponse;
import com.payment.mypayment.common.type.PgId;
import com.payment.mypayment.common.type.ResponseType;
import com.payment.mypayment.controller.common.dto.AmountInfo;
import com.payment.mypayment.controller.common.dto.Meta;
import com.payment.mypayment.controller.payment.dto.create.CreateRequest;
import com.payment.mypayment.controller.payment.dto.create.CreateResponse;
import com.payment.mypayment.controller.payment.dto.create.CreateResponseBody;
import com.payment.mypayment.exception.PgFailException;
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

        ClientResponse clientResponse = getPgResponse(createRequest, amountInfo);

        log.info(">>> 카카오페이 결제 생성 결과 : " + clientResponse);

        if(clientResponse.isSuccess()){

            KakaoPayCreateResponse kakaoPayCreateResponse = (KakaoPayCreateResponse) clientResponse.getPgResponse();

            CreateResponseBody body = CreateResponseBody.builder()
                    .orderNo(createRequest.getOrderNo())
                    .paymentId(paymentId)
                    .tid(kakaoPayCreateResponse.getTid())
                    .redirectPcUrl(kakaoPayCreateResponse.getNextRedirectPcUrl())
                    .redirectAppUrl(kakaoPayCreateResponse.getNextRedirectAppUrl())
                    .redirectMobileUrl(kakaoPayCreateResponse.getNextRedirectMobileUrl())
                    .appScheme(CreateResponseBody.AppScheme.builder()
                            .aos(kakaoPayCreateResponse.getAndroidAppScheme())
                            .ios(kakaoPayCreateResponse.getIosAppScheme()).build())
                    .build();

            return CreateResponse.ofSuccess(body);

        }else {
            if(ObjectUtils.isEmpty(clientResponse.getPgCode())){
                return CreateResponse.ofFail(ResponseType.COMMUNICATION_ERROR);
            }else {
                throw new PgFailException(PgId.KAKAO_PAY.getPgId()
                                            , clientResponse.getPgCode()
                                            , clientResponse.getPgMessage());
            }
        }
    }

    public ClientResponse getPgResponse(CreateRequest createRequest, AmountInfo amountInfo) {

        ClientResponse clientResponse;
        ResponseEntity<String> pgResponse;

        KakaoPayCreateRequest kakaoPayCreateRequest = KakaoPayCreateRequest.builder()
                .cid(CID)
                .partnerOrderId(createRequest.getOrderNo())
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
                        .build();
            }
        }catch (Exception e){
            log.error("[KakaoPayCreateService.getPgResponse] Exception - " + e);
            clientResponse = ClientResponse.builder()
                    .isSuccess(false)
                    .build();
        }

        return clientResponse;
    }
}
