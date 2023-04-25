package com.payment.mypayment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/hello")
    public void sayHello(){
      log.info("hello!");
    }
}
