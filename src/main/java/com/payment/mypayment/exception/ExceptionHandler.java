package com.payment.mypayment.exception;

import com.payment.mypayment.controller.common.dto.Meta;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(ValidationException.class)
    @ResponseBody
    public Meta handleValidationException(ValidationException e, final HttpServletRequest request) {
        log.error("[ExceptionHandler.handleValidationException][URL : {}] ValidationException : {} - {}"
                ,request.getRequestURI(), e.getResponseType().toString(), e.getDetailMessage());
        return Meta.ofFail(e.getResponseType(), e.getDetailMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<Meta> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, final HttpServletRequest request) {
        log.error("[ExceptionHandler.handleHttpMessageNotReadableException][URL : {}] HttpMessageNotReadableException : {}"
                ,request.getRequestURI(), e.getMessage());

        Meta meta = Meta.ofWrongParameterFail(e.getMessage());
        return new ResponseEntity<>(meta, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.OK)
    @org.springframework.web.bind.annotation.ExceptionHandler(PgFailException.class)
    @ResponseBody
    public Meta handlePgFailException(PgFailException e) {
        log.error("[ExceptionHandler.handlePgFailException][PG ID : {}] PgFailException : {} "
                , e.getPgId(), e.getPgFailCode() + "/" + e.getPgFailMessage());
        return Meta.ofPgFail(e.getPgFailCode(), e.getPgFailMessage());
    }
}
