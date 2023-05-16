package com.payment.mypayment.common.util;

import com.payment.mypayment.common.type.ResponseType;
import com.payment.mypayment.controller.common.dto.Product;
import com.payment.mypayment.controller.payment.dto.approve.ApproveRequest;
import com.payment.mypayment.controller.payment.dto.create.CreateRequest;
import com.payment.mypayment.exception.ValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtil {

    public static void validateCreateRequest(CreateRequest createRequest, BindingResult bindingResult) {

        // 파라미터 정합성 검증
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            throw new ValidationException(ResponseType.WRONG_PARAMETER, errorList.toString());
        }

        // 상품 금액 타당성 검증
        validateProductList(createRequest.getProductList());

        // TODO 이미 승인된 주문번호인지 검증
    }

    private static void validateProductList(List<Product> productList) {
        for(Product product : productList) {
            if(product.getTransactionAmount() == product.getProductAmount() - product.getDiscountAmount())
                throw new ValidationException(ResponseType.WRONG_PARAMETER, "상품의 금액이 맞지않습니다.");
        }
    }

    public static void validateApproveRequest(ApproveRequest approveRequest, BindingResult bindingResult) {
        // TODO 승인요청 데이터 검증 , 생성요청 데이터가 있는지 확인, 이미 승인되었는지 확인
        
    }
}
