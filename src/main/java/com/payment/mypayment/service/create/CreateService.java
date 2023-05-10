package com.payment.mypayment.service.create;

import com.payment.mypayment.common.type.ResponseType;
import com.payment.mypayment.common.util.AmountUtil;
import com.payment.mypayment.common.util.StringUtil;
import com.payment.mypayment.common.util.ValidationUtil;
import com.payment.mypayment.controller.common.dto.AmountInfo;
import com.payment.mypayment.controller.payment.dto.create.CreateRequest;
import com.payment.mypayment.controller.payment.dto.create.CreateResponse;
import com.payment.mypayment.exception.PgFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateService {

    private final KakaoPayCreateService kakaoPayCreateService;

    public CreateResponse create(String pgId, CreateRequest request, BindingResult bindingResult) {

        ValidationUtil.validateCreateRequest(request, bindingResult);

        String paymentId = StringUtil.getPaymentId();
        AmountInfo amountInfo = AmountUtil.getAmountInfo(request.getProductList());

        try{
            switch (pgId){
                case "kakao-pay" : return kakaoPayCreateService.create(paymentId, request, amountInfo);
                case "naver-pay" : return null;
                default:
                    String errorMessage = "요청된 PG ID : " +  pgId;
                    log.error("[CreateService.create][orderNo : {}]{}", request.getOrderNo(), errorMessage);
                    return CreateResponse.ofFail(ResponseType.WRONG_PG_ID, errorMessage);
            }
        }catch (PgFailException e){
            throw e;
        }catch (Exception e){
            return CreateResponse.ofFail(ResponseType.PROCESSING_ERROR, e.getLocalizedMessage());
        }
    }
}
