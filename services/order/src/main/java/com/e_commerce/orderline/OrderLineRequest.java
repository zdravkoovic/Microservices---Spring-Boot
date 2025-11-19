package com.e_commerce.orderline;

public record OrderLineRequest(
    Integer id,
    Integer orderId,
    Integer productId,
    double quantity
) {
    
}
