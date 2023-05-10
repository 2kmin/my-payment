package com.payment.mypayment.exception;

import com.payment.mypayment.common.type.ResponseType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
@Getter
@Slf4j
public class ValidationException extends RuntimeException{

    private final ResponseType responseType;
    private final String detailMessage;

    public ValidationException(ResponseType responseType, String detailMessage ) {
        this.responseType = responseType;
        this.detailMessage = detailMessage;
    }
}
