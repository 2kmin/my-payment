package com.payment.mypayment.service.create;

import com.payment.mypayment.client.dto.kakaoPay.KakaoPayApproveResponse;
import com.payment.mypayment.common.type.PaymentMethodType;
import com.payment.mypayment.common.type.PgId;
import com.payment.mypayment.common.type.TransactionType;
import com.payment.mypayment.common.util.DateUtil;
import com.payment.mypayment.controller.payment.dto.approve.ApproveResponseBody;
import com.payment.mypayment.entity.Payment;
import com.payment.mypayment.entity.PaymentMethod;
import com.payment.mypayment.entity.PaymentRequest;
import com.payment.mypayment.repository.PaymentMethodRepository;
import com.payment.mypayment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ApproveTransactionService {

    private final PaymentRepository paymentRepository;
    private final PaymentMethodRepository paymentMethodRepository;

    @Transactional
    public void savePayment(PaymentRequest paymentRequest, KakaoPayApproveResponse kakaoPayApproveResponse) {
        Payment payment = Payment.builder()
                .paymentId(paymentRequest.getPaymentId())
                .orderNo(paymentRequest.getOrderNo())
                .pgTransactionId(kakaoPayApproveResponse.getTid())
                .pgMatchedId(kakaoPayApproveResponse.getAid())
                .pgId(PgId.KAKAO_PAY.getPgId())
                .transactionType(TransactionType.PAYMENT.name())
                .memberNo(paymentRequest.getMemberNo())
                .transactionAmount(paymentRequest.getTransactionAmount())
                .productCount(paymentRequest.getTotalCount())
                .productAmount(paymentRequest.getTotalProductAmount())
                .discountAmount(paymentRequest.getTotalDiscountAmount())
                .taxAmount(paymentRequest.getTotalTaxAmount())
                .taxFreeAmount(paymentRequest.getTotalTaxFreeAmount())
                .taxSupplyAmount(paymentRequest.getTotalTaxSupplyAmount())
                .vatAmount(paymentRequest.getTotalVatAmount())
                .pgDiscountAmount(kakaoPayApproveResponse.getAmount().getDiscount())
                .approvedAt(DateUtil.convertTimezoneStrToLDT(kakaoPayApproveResponse.getApprovedAt()))
                .platform("") // TODO 결제 PLATFORM
                .build();
        paymentRepository.save(payment);
    }

    @Transactional
    public void savePaymentMethod(String orderNo, String paymentId, KakaoPayApproveResponse kakaoPayApproveResponse) {

        int totalAmt = kakaoPayApproveResponse.getAmount().getTotal();
        int pointAmt = kakaoPayApproveResponse.getAmount().getPoint();
        int discountAmt = kakaoPayApproveResponse.getAmount().getDiscount();
        int totalSubDiscountAmt = pointAmt + discountAmt;

        String mainPaymentMethodCode = PaymentMethodType.getCommonPaymentMethodType(PgId.KAKAO_PAY, kakaoPayApproveResponse.getPaymentMethodType());
        LocalDateTime approvedAt = DateUtil.convertTimezoneStrToLDT(kakaoPayApproveResponse.getApprovedAt());

        if(totalAmt != totalSubDiscountAmt){
            PaymentMethod mainPaymentMethod = createMainPaymentMethodKakao(orderNo, paymentId, mainPaymentMethodCode, totalAmt, totalSubDiscountAmt
                                                                    , kakaoPayApproveResponse.getCardInfo(), approvedAt);
            paymentMethodRepository.save(mainPaymentMethod);
        }

        if (pointAmt > 0) {
            PaymentMethod pointPaymentMethod = createPaymentMethodNoCard(orderNo, paymentId, PaymentMethodType.CommonPaymentMethodType.pg_point.name(), pointAmt, approvedAt);
            paymentMethodRepository.save(pointPaymentMethod);
        }

        if (discountAmt > 0) {
            PaymentMethod discountPaymentMethod = createPaymentMethodNoCard(orderNo, paymentId, PaymentMethodType.CommonPaymentMethodType.pg_discount.name(), discountAmt, approvedAt);
            paymentMethodRepository.save(discountPaymentMethod);
        }
    }

    public List<ApproveResponseBody.PaymentMethod> getResponsePaymentMethodList(String paymentId) {
        List<ApproveResponseBody.PaymentMethod> responsePaymentMethodList = new ArrayList<>();
        List<PaymentMethod> paymentMethodList= paymentMethodRepository.findByPaymentId(paymentId);

        for(PaymentMethod paymentMethod : paymentMethodList){
            responsePaymentMethodList.add(ApproveResponseBody.PaymentMethod.builder()
                    .method(paymentMethod.getPaymentMethod())
                    .amount(paymentMethod.getTransactionAmount())
                    .build());
        }
        return responsePaymentMethodList;
    }

    private PaymentMethod createMainPaymentMethodKakao(String orderNo, String paymentId, String paymentMethodCode, int totalAmt, int totalSubDiscountAmt
                                                , KakaoPayApproveResponse.CardInfo cardInfo, LocalDateTime approvedAt) {

        PaymentMethod.PaymentMethodBuilder paymentMethodBuilder = PaymentMethod.builder()
                .paymentId(orderNo)
                .orderNo(paymentId)
                .paymentMethod(paymentMethodCode)
                .transactionType(TransactionType.PAYMENT.name())
                .approvedAt(approvedAt);

        if (paymentMethodCode.equals(PaymentMethodType.CommonPaymentMethodType.card.name())) {
            paymentMethodBuilder
                    .transactionAmount(totalAmt - totalSubDiscountAmt)
                    .cardCompany(cardInfo.getPurchaseCorp()) // TODO PG사 카드명 -> 공통코드로 변경
                    .cardType(cardInfo.getCardType())
                    .installment(Integer.parseInt(cardInfo.getInstallMonth()))
                    .cardApprovedNo(cardInfo.getApprovedId());
        } else if (paymentMethodCode.equals(PaymentMethodType.CommonPaymentMethodType.pay_money.name())) {
            paymentMethodBuilder
                    .transactionAmount(totalAmt - totalSubDiscountAmt);
        } else {
            String errorMsg = "CARD와 MONEY 외의 주 결제수단이 들어왔습니다 - <" + paymentMethodCode + ">";
            log.error("[ApproveTransactionService.savePaymentMethod]" + errorMsg);
            throw new RuntimeException(errorMsg);
        }
        return paymentMethodBuilder.build();
    }


    private PaymentMethod createPaymentMethodNoCard(String orderNo, String paymentId, String paymentMethodCode, int transactionAmount, LocalDateTime approvedAt) {
        return PaymentMethod.builder()
                .paymentId(orderNo)
                .orderNo(paymentId)
                .paymentMethod(paymentMethodCode)
                .transactionType(TransactionType.PAYMENT.name())
                .transactionAmount(transactionAmount)
                .approvedAt(approvedAt)
                .build();
    }

}
