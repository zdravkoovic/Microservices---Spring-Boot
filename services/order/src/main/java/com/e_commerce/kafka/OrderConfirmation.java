package com.e_commerce.kafka;

import java.math.BigDecimal;
import java.util.List;

import com.e_commerce.customer.CustomerResponse;
import com.e_commerce.order.PaymentMethod;
import com.e_commerce.product.PurchaseResponse;

public record OrderConfirmation(
    String orderReference,
    BigDecimal totalAmount,
    PaymentMethod paymentMethod,
    CustomerResponse customer,
    List<PurchaseResponse> products
) {

}
