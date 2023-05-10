package com.payment.mypayment.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponse {

    private boolean isSuccess;
    private String pgCode;
    private String pgMessage;
    private Object pgResponse;
}
