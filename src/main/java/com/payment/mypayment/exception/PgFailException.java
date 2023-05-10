package com.payment.mypayment.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.OK)
@Getter
@Slf4j
public class PgFailException extends RuntimeException {

    private final String pgId;
    private final String pgFailCode;
    private final String pgFailMessage;

    public PgFailException(String pgId, String pgFailCode, String pgFailMessage){
        this.pgId = pgId;
        this.pgFailCode = pgFailCode;
        this.pgFailMessage = pgFailMessage;
    }
}
