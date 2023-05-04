package com.payment.mypayment.controller.payment;

import com.payment.mypayment.common.type.ResponseType;
import com.payment.mypayment.common.util.ValidationUtil;
import com.payment.mypayment.controller.payment.dto.create.CreateRequest;
import com.payment.mypayment.controller.payment.dto.create.CreateResponse;
import com.payment.mypayment.exception.ValidationException;
import com.payment.mypayment.service.CreateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
//@RequiredArgsConstructor
@AllArgsConstructor
@RequestMapping("/payment/{pg_id}")
public class PaymentController {

    private final CreateService createService;

    @PostMapping("/create")
    public CreateResponse create(@NotBlank(message = "pgId 누락") @PathVariable(value = "pg_id") String pgId,
                                 @RequestBody @Valid CreateRequest request,
                                 BindingResult bindingResult){

        log.info("CREATE! : [{}][{}]", pgId, request);

        return createService.create(pgId, request, bindingResult);
    }

    @PostMapping("/apprve")
    public void approve(@RequestBody @Valid String request, BindingResult bindingResult){

        log.info("approve");
    }


}
