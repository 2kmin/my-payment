package com.payment.mypayment.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    @Qualifier(value = "paymentInterceptor")
    private PaymentInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("Set Interceptor {}", interceptor.getClass());

        List<String> excludeList = new ArrayList<String>();

        registry.addInterceptor(interceptor)
                .addPathPatterns("/**");
    }
}
