package com.payment.mypayment.service.create;

import com.payment.mypayment.common.type.TransactionType;
import com.payment.mypayment.controller.common.dto.AmountInfo;
import com.payment.mypayment.entity.PaymentRequest;
import com.payment.mypayment.entity.Product;
import com.payment.mypayment.repository.PaymentRequestRepository;
import com.payment.mypayment.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CreateTransactionService {

    private final PaymentRequestRepository paymentRequestRepository;
    private final ProductRepository productRepository;

    public void savePaymentRequest(String paymentId, String orderNo, String memberNo, AmountInfo amountInfo) {

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .paymentId(paymentId)
                .orderNo(orderNo)
                .memberNo(memberNo)
                .transactionAmount(amountInfo.getTransactionAmount())
                .totalCount(amountInfo.getTotalCount())
                .totalProductAmount(amountInfo.getTotalProductAmount())
                .totalDiscountAmount(amountInfo.getTotalDiscountAmount())
                .totalTaxAmount(amountInfo.getTotalTaxAmount())
                .totalTaxFreeAmount(amountInfo.getTotalTaxFreeAmount())
                .totalTaxSupplyAmount(amountInfo.getTotalTaxSupplyAmount())
                .totalVatAmount(amountInfo.getTotalVatAmount())
                .build();

        paymentRequestRepository.save(paymentRequest);
    }


    public void saveProduct(String paymentId, String orderNo, List<AmountInfo.ProductTaxInfo> productTaxInfoList) {

        List<Product> productList = new ArrayList<>();

        for(AmountInfo.ProductTaxInfo productTaxInfo : productTaxInfoList){
            Product product = Product.builder()
                    .paymentId(paymentId)
                    .orderNo(orderNo)
                    .productName(productTaxInfo.getProduct().getProductName())
                    .productCode(productTaxInfo.getProduct().getProductCode())
                    .taxType(productTaxInfo.getProduct().getTaxType())
                    .transactionType(TransactionType.PAYMENT.name())
                    .productCount(productTaxInfo.getProduct().getCount())
                    .productAmount(productTaxInfo.getProduct().getProductAmount())
                    .discountAmount(productTaxInfo.getProduct().getDiscountAmount())
                    .transactionAmount(productTaxInfo.getProduct().getTransactionAmount())
                    .taxAmount(productTaxInfo.getTaxAmount())
                    .taxFreeAmount(productTaxInfo.getTaxFreeAmount())
                    .taxSupplyAmount(productTaxInfo.getTaxSupplyAmount())
                    .vatAmount(productTaxInfo.getVatAmount())
                    .build();
            productList.add(product);
        }
        productRepository.saveAll(productList);
    }
}
