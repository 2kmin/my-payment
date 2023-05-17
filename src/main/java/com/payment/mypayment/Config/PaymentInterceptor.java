package com.payment.mypayment.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class PaymentInterceptor implements HandlerInterceptor {

/*
preHandle() : 맵핑되기 전 처리를 해주면 됩니다.
postHandle() : 맵핑되고난 후 처리를 해주면 됩니다. (성공일때만 찍힘)
afterCompletion() : 모든 작업이 완료된 후 실행 됩니다. (모든 응답 다찍힘)

TODO
servlet의 경우 request데이터를 한번만 꺼내 쓸수 있기 때문에 로깅용으로 인터셉터에서 꺼냈을경우
컨트롤러에 요청데이터가 정상 전달되지 못함  => filter 사용으로 변경 예정

 */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        log.info("================ Before preHandle");
        log.info("[PaymentInterceptor.preHandle][URL : {}] REQUEST DATA : {}", request.getRequestURI(),"");

        return true;
    }


    @Override
    public void postHandle( HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler,
                            ModelAndView modelAndView) {
        // 성공일때만 찍힘 
        log.info("================ Method postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        // 모든 응답이 다찍힘
        log.info("================ Method afterCompletion");
    }
}