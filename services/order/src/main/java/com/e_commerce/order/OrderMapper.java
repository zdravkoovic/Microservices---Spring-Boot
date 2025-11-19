package com.e_commerce.order;

import org.springframework.stereotype.Service;

import com.e_commerce.product.PurchaseResponse;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest request) {
        
        if(request == null) return null;
        
        return Order.builder()
                .id(request.id())
                .reference(request.reference())
                .totalAmount(request.totalAmount())
                .paymentMethod(request.paymentMethod())
                .customerId(request.customerId())
                .build();
    }

    public OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getReference(),
            order.getTotalAmount(),
            order.getPaymentMethod(),
            order.getCustomerId()
        );
    }

    public PurchaseResponse toOrderProducer(PurchaseResponse product, double requestedQuantity)
    {
        return new PurchaseResponse(
            product.productId(),
            product.name(),
            product.description(),
            product.price(),
            requestedQuantity
        );
    }

}
