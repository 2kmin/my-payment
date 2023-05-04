package com.payment.mypayment.service;

import com.payment.mypayment.common.type.ResponseType;
import com.payment.mypayment.common.util.AmountUtil;
import com.payment.mypayment.common.util.ValidationUtil;
import com.payment.mypayment.controller.common.dto.AmountInfo;
import com.payment.mypayment.controller.payment.dto.create.CreateRequest;
import com.payment.mypayment.controller.payment.dto.create.CreateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Slf4j
@Service
public class CreateService {
    public CreateResponse create(String pgId, CreateRequest request, BindingResult bindingResult) {

        ValidationUtil.validateCreateRequest(request, bindingResult);

        AmountInfo amountInfo = AmountUtil.getAmountInfo(request.getProductList());

        switch (pgId){
            case "kakao-pay" : return null;
            case "naver-pay" : return null;
            default:
                String errorMessage = "요청된 PG ID : " +  pgId;
                log.error("[CreateService.create][orderNo : {}]{}", request.getOrderNo(), errorMessage);
                return CreateResponse.ofFail(ResponseType.WRONG_PG_ID, errorMessage);
        }
    }
}
