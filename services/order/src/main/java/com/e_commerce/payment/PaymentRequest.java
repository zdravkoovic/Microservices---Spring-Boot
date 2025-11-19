package com.e_commerce.payment;

import java.math.BigDecimal;

import com.e_commerce.customer.CustomerResponse;
import com.e_commerce.order.PaymentMethod;

public record PaymentRequest(
    BigDecimal amount,
    PaymentMethod paymentMethod,
    Integer orderId,
    String orderReference,
    CustomerResponse customer
) {

}
