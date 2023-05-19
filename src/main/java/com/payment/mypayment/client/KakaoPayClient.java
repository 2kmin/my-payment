package com.payment.mypayment.client;

import com.payment.mypayment.Config.FeignConfiguration;
import com.payment.mypayment.client.dto.kakaoPay.KakaoPayApproveRequest;
import com.payment.mypayment.client.dto.kakaoPay.KakaoPayCreateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kakaoPayClient", url = "https://kapi.kakao.com", configuration = FeignConfiguration.class)
public interface KakaoPayClient {

    @PostMapping(value = "/v1/payment/ready",headers = "Authorization= KakaoAK ${key.kakao.adminKey}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<String> requestCreate(@RequestBody KakaoPayCreateRequest request);

    @PostMapping(value = "/v1/payment/approve",headers = "Authorization= KakaoAK ${key.kakao.adminKey}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<String> requestApprove(KakaoPayApproveRequest pgRequest);
}