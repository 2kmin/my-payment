package com.payment.mypayment.common.type

import lombok.Getter

@Getter
enum class PgRequestType {
    CREATE,
    APPROVE,
    CANCEL,
    PARTIAL_CANCEL
}