package com.payment.mypayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class MyPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyPaymentApplication.class, args);
    }

}
