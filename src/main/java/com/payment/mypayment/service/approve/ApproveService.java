package com.payment.mypayment.service.approve;

import com.payment.mypayment.common.type.ResponseType;
import com.payment.mypayment.common.util.ValidationService;
import com.payment.mypayment.controller.payment.dto.approve.ApproveRequest;
import com.payment.mypayment.controller.payment.dto.approve.ApproveResponse;
import com.payment.mypayment.entity.PaymentRequest;
import com.payment.mypayment.exception.PgFailException;
import com.payment.mypayment.repository.PaymentRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApproveService {

    private final ValidationService validationService;
    private final PaymentRequestRepository paymentRequestRepository;
    private final KakaoPayApproveService kakaoPayApproveService;

    public ApproveResponse approve(String pgId, ApproveRequest approveRequest, BindingResult bindingResult) {

        PaymentRequest paymentRequest;
        Optional<PaymentRequest> optPaymentRequest = paymentRequestRepository.findByPaymentId(approveRequest.getPaymentId());

        validationService.validateApproveRequest(approveRequest, bindingResult, optPaymentRequest);

        paymentRequest = optPaymentRequest.get();
        try{
            switch (pgId){
                case "kakao-pay" : return kakaoPayApproveService.approve(approveRequest, paymentRequest);
                case "naver-pay" : return null;
                default:
                    String errorMessage = "요청된 PG ID : " +  pgId;
                    log.error("[ApproveService.create][paymentId : {}]{}", approveRequest.getPaymentId(), errorMessage);
                    return ApproveResponse.ofFail(ResponseType.WRONG_PG_ID, errorMessage);
            }
        }catch (PgFailException e){
            throw e;
        }catch (Exception e){
            return ApproveResponse.ofFail(ResponseType.PROCESSING_ERROR, e.getLocalizedMessage());
        }
    }
}
