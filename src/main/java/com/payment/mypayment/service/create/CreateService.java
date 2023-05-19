package com.payment.mypayment.service.create;

import com.payment.mypayment.common.type.ResponseType;
import com.payment.mypayment.common.util.AmountUtil;
import com.payment.mypayment.common.util.StringUtil;
import com.payment.mypayment.common.util.ValidationService;
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

    private final ValidationService validationService;
    private final CreateTransactionService createTransactionService;
    private final KakaoPayCreateService kakaoPayCreateService;

    public CreateResponse create(String pgId, CreateRequest request, BindingResult bindingResult) {

        validationService.validateCreateRequest(request, bindingResult);

        String paymentId = StringUtil.getPaymentId();
        String orderNo = request.getOrderNo();
        AmountInfo amountInfo = AmountUtil.getAmountInfo(request.getProductList());

        try{
        createTransactionService.savePaymentRequest(paymentId
                , orderNo
                , request.getMemberNo()
                , amountInfo);

        createTransactionService.saveProduct(paymentId
                , orderNo
                , amountInfo.getProductTaxInfoList());

            switch (pgId){
                case "kakao-pay" : return kakaoPayCreateService.create(paymentId, request, amountInfo);
                case "naver-pay" : return null;
                default:
                    String errorMessage = "요청된 PG ID : " +  pgId;
                    log.error("[CreateService.create][orderNo : {}]{}", orderNo, errorMessage);
                    return CreateResponse.ofFail(ResponseType.WRONG_PG_ID, errorMessage);
            }
        }catch (PgFailException e){
            throw e;
        }catch (Exception e){
            log.error("[[CreateService.create][orderNo : {}] 결제생성 내부처리 오류 - {}", orderNo, e.getLocalizedMessage());
            return CreateResponse.ofFail(ResponseType.PROCESSING_ERROR, e.getLocalizedMessage());
        }
    }
}
