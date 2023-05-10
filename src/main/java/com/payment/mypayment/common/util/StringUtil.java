package com.payment.mypayment.common.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class StringUtil {

    public static String getPaymentId(){
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        return "P" + now.format(formatter) + UUID.randomUUID().toString().substring(0,8);
    }
}
