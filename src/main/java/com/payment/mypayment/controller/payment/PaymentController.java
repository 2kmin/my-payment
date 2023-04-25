package com.payment.mypayment.controller.payment;

import com.payment.mypayment.common.type.ResponseType;
import com.payment.mypayment.controller.payment.dto.create.CreateRequest;
import com.payment.mypayment.exception.ValidationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/payment/{pg_id}")
public class PaymentController {

    @PostMapping("/create")
    public String create(@NotBlank(message = "pgId 누락") @PathVariable(value = "pg_id") String pgId,
                       @RequestBody @Valid CreateRequest request,
                       BindingResult bindingResult){

        log.info("CREATE! : [{}][{}]", pgId, request);

        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            throw new ValidationException(ResponseType.WRONG_PARAMETER, errorList.toString());
        }
        return "ok";
    }

    @PostMapping("/apprve")
    public void approve(@RequestBody @Valid String request, BindingResult bindingResult){

        log.info("approve");
    }


}
