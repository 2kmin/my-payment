package com.payment.mypayment.service.approve;

import com.payment.mypayment.common.type.ResponseType;
import com.payment.mypayment.common.util.ValidationUtil;
import com.payment.mypayment.controller.payment.dto.approve.ApproveRequest;
import com.payment.mypayment.controller.payment.dto.approve.ApproveResponse;
import com.payment.mypayment.controller.payment.dto.create.CreateResponse;
import com.payment.mypayment.exception.PgFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApproveService {

    private final KakaoPayApproveService kakaoPayApproveService;

    public ApproveResponse approve(String pgId, ApproveRequest request, BindingResult bindingResult) {

        ValidationUtil.validateApproveRequest(request, bindingResult);

        try{
            switch (pgId){
                case "kakao-pay" : return kakaoPayApproveService.approve(request);
                case "naver-pay" : return null;
                default:
                    String errorMessage = "요청된 PG ID : " +  pgId;
                    log.error("[ApproveService.create][paymentId : {}]{}", request.getPaymentId(), errorMessage);
                    return ApproveResponse.ofFail(ResponseType.WRONG_PG_ID, errorMessage);
            }
        }catch (PgFailException e){
            throw e;
        }catch (Exception e){
            return ApproveResponse.ofFail(ResponseType.PROCESSING_ERROR, e.getLocalizedMessage());
        }
    }
}
