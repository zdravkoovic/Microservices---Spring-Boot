package com.e_commerce.notification;

import java.math.BigDecimal;

import com.e_commerce.payment.PaymentMethod;

public record PaymentNotificationRequest(
    String orderReference,
    BigDecimal amount,
    PaymentMethod paymentMethod,
    String customerFirstname,
    String customerLastname,
    String customerEmail
) {

}
