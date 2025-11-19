package com.e_commerce.kafka.order;

import java.math.BigDecimal;
import java.util.List;

import com.e_commerce.kafka.payment.PaymentMethod;

public record OrderConfirmation(
    String orderReference,
    BigDecimal totalAmount,
    PaymentMethod paymentMethod,
    Customer customer,
    List<Product> products
) {
    
}
