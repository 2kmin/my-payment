package com.payment.mypayment.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.mypayment.common.type.ResponseType;
import com.payment.mypayment.exception.PgFailException;
import feign.Logger;
import feign.Response;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.apache.http.HttpException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Configuration
//public class FeignConfiguration implements ErrorDecoder {
public class FeignConfiguration {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    Encoder formEncoder() {
        return new feign.form.FormEncoder();
    }

//    @Override
//    public Exception decode(String methodKey, Response response) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            InputStream inputStream = response.body().asInputStream();
//
//            Map<String, Object> map = objectMapper.readValue(inputStream, Map.class);
//
//            if(response.status() != 200 && !ObjectUtils.isEmpty(map.get("code")) && !ObjectUtils.isEmpty(map.get("msg"))){
//                return new PgFailException(ResponseType.PG_FAIL.getCode(), String.valueOf(map.get("code")), (String) map.get("msg"));
//            }else{
//                return new
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }

}
