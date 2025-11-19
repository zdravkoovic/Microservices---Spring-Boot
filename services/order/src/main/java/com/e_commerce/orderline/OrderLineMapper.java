package com.e_commerce.orderline;

import org.springframework.stereotype.Service;

import com.e_commerce.order.Order;

@Service
public class OrderLineMapper {

    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                        .id(request.id())
                        .quantity(request.quantity())
                        .order(
                            Order.builder()
                                 .id(request.orderId())
                                 .build()
                        )
                        .productId(request.productId())
                        .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine request) {
        return new OrderLineResponse(
            request.getId(),
            request.getQuantity()
        );
    }

}
