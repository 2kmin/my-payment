package com.payment.mypayment.service.approve;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.mypayment.client.KakaoPayClient;
import com.payment.mypayment.client.dto.ClientResponse;
import com.payment.mypayment.client.dto.kakaoPay.KakaoPayApproveRequest;
import com.payment.mypayment.client.dto.kakaoPay.KakaoPayApproveResponse;
import com.payment.mypayment.common.type.PgId;
import com.payment.mypayment.common.type.ResponseType;
import com.payment.mypayment.controller.payment.dto.approve.ApproveRequest;
import com.payment.mypayment.controller.payment.dto.approve.ApproveResponse;
import com.payment.mypayment.controller.payment.dto.approve.ApproveResponseBody;
import com.payment.mypayment.entity.PaymentRequest;
import com.payment.mypayment.exception.PgFailException;
import com.payment.mypayment.service.create.ApproveTransactionService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoPayApproveService {

    @Value("${key.kakao.cid}")
    private String CID;

    private final KakaoPayClient kakaoPayClient;
    private final ObjectMapper objectMapper;
    private final ApproveTransactionService approveTransactionService;

    public ApproveResponse approve(ApproveRequest approveRequest, PaymentRequest paymentRequest) {

        ClientResponse clientResponse = getPgResponse(approveRequest, paymentRequest);

        if(clientResponse.isSuccess()){

            String orderNo = paymentRequest.getOrderNo();
            String paymentId = paymentRequest.getPaymentId();

            KakaoPayApproveResponse kakaoPayApproveResponse = (KakaoPayApproveResponse) clientResponse.getPgResponse();

            approveTransactionService.savePayment(paymentRequest, kakaoPayApproveResponse);

            approveTransactionService.savePaymentMethod(orderNo, paymentId, kakaoPayApproveResponse);

            ApproveResponseBody body = ApproveResponseBody.builder()
                    .orderNo(orderNo)
                    .paymentId(paymentId)
                    .amount(kakaoPayApproveResponse.getAmount().getTotal())
                    .paymentMethod(approveTransactionService.getResponsePaymentMethodList(paymentId))
                    .approveAt(kakaoPayApproveResponse.getApprovedAt())
                    .build();

            return ApproveResponse.ofSuccess(body);

        }else{
            if(ObjectUtils.isEmpty(clientResponse.getPgCode())) {
                return ApproveResponse.ofFail(ResponseType.COMMUNICATION_ERROR);
            }else {
                throw new PgFailException(PgId.KAKAO_PAY.getPgId()
                        , clientResponse.getPgCode()
                        , clientResponse.getPgMessage());
            }
        }
    }

    private ClientResponse getPgResponse(ApproveRequest approveRequest, PaymentRequest paymentRequest) {

        ClientResponse clientResponse;
        ResponseEntity<String> pgResponse;

        KakaoPayApproveRequest pgRequest = KakaoPayApproveRequest.builder()
                .cid(CID)
                .tid(approveRequest.getPgTransactionId())
                .partnerOrderId(paymentRequest.getOrderNo())
                .partnerUserId(paymentRequest.getMemberNo())
                .pgToken(approveRequest.getPgToken())
                .build();

        try{
            pgResponse = kakaoPayClient.requestApprove(pgRequest);

            KakaoPayApproveResponse kakaoPayApproveResponse = objectMapper.readValue(pgResponse.getBody(), KakaoPayApproveResponse.class);

            clientResponse = ClientResponse.builder()
                    .isSuccess(true)
                    .pgResponse(kakaoPayApproveResponse)
                    .build();

        }catch (FeignException e){
            log.error("[KakaoPayApproveeService.getPgResponse] 카카오페이 결제 승인 실패 FeignException - " + e);
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
                log.error("[KakaoPayApproveeService.getPgResponse] JsonProcessingException - " + e);
                clientResponse = ClientResponse.builder()
                        .isSuccess(false)
                        .build();
            }
        }catch (Exception e){
            log.error("[KakaoPayApproveeService.getPgResponse] Exception - " + e);
            clientResponse = ClientResponse.builder()
                    .isSuccess(false)
                    .build();
        }
        return clientResponse;
    }
}
