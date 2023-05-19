package com.payment.mypayment.common.util;

import com.payment.mypayment.common.type.ResponseType;
import com.payment.mypayment.controller.common.dto.Product;
import com.payment.mypayment.controller.payment.dto.approve.ApproveRequest;
import com.payment.mypayment.controller.payment.dto.create.CreateRequest;
import com.payment.mypayment.entity.Payment;
import com.payment.mypayment.entity.PaymentRequest;
import com.payment.mypayment.exception.ValidationException;
import com.payment.mypayment.repository.PaymentRepository;
import com.payment.mypayment.repository.PaymentRequestRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final PaymentRequestRepository paymentRequestRepository;
    private final PaymentRepository paymentRepository;

    public void validateCreateRequest(CreateRequest createRequest, BindingResult bindingResult) {

        // 파라미터 정합성 검증
        validateBindingResult(bindingResult);

        // 상품 금액 타당성 검증
        validateProductList(createRequest.getProductList());

        // 이미 승인된 주문번호인지 검증
        validateAlreadyExistPaymentByOrderNo(createRequest.getOrderNo());
    }

    public void validateApproveRequest(ApproveRequest approveRequest, BindingResult bindingResult, Optional<PaymentRequest> paymentRequest) {

        // 파라미터 정합성 검증
        validateBindingResult(bindingResult);

        // 요청된 paymentId인지 확인
        validateRequested(approveRequest.getPaymentId(), paymentRequest);

        // 이미 승인되었는지 확인
        validateAlreadyExistPaymentByPaymentId(approveRequest.getPaymentId());
    }

    private void validateAlreadyExistPaymentByPaymentId(String paymentId) {
        Optional<Payment> optPayment = paymentRepository.findByPaymentId(paymentId);
        if(optPayment.isPresent()) throw new ValidationException(ResponseType.ALREADY_SUCCESS
                                                    ,"[paymentId : "+paymentId+"]는 이미 성공처리 되었습니다.");
    }

    private void validateAlreadyExistPaymentByOrderNo(String orderNo) {
        Optional<Payment> optPayment = paymentRepository.findByOrderNo(orderNo);
        if(optPayment.isPresent()) throw new ValidationException(ResponseType.ALREADY_SUCCESS
                                                  ,"[orderNo : "+orderNo+"]는 이미 성공처리 되었습니다.");
    }

    private void validateBindingResult(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            throw new ValidationException(ResponseType.WRONG_PARAMETER, errorList.toString());
        }
    }

    private void validateProductList(List<Product> productList) {
        for(Product product : productList) {
            if(product.getTransactionAmount() != product.getProductAmount() - product.getDiscountAmount())
                throw new ValidationException(ResponseType.WRONG_AMOUNT, "상품은 [결제금액 == 상품금액 - 할인금액] 이어야합니다.");
        }
    }

    private void validateRequested(String paymentId, Optional<PaymentRequest> paymentRequest) {
        if(paymentRequest.isEmpty()){
            throw new ValidationException(ResponseType.WRONG_PAYMENT_ID, "등록되지 않은 paymentId 입니다 - "+paymentId);
        }
    }

}
