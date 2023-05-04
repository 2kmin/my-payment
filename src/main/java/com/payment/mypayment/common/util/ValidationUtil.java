package com.payment.mypayment.common.util;

import com.payment.mypayment.common.type.ResponseType;
import com.payment.mypayment.controller.common.dto.Product;
import com.payment.mypayment.controller.payment.dto.create.CreateRequest;
import com.payment.mypayment.exception.ValidationException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtil {

    public static void validateCreateRequest(CreateRequest createRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            throw new ValidationException(ResponseType.WRONG_PARAMETER, errorList.toString());
        }
        validateProductList(createRequest.getProductList());
    }

    private static void validateProductList(List<Product> productList) {
        for(Product product : productList) {
            if(product.getAmount() == product.getProductAmount() - product.getDiscountAmount())
                throw new ValidationException(ResponseType.WRONG_PARAMETER, "상품의 금액이 맞지않습니다.");
        }
    }
}
