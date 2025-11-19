package com.e_commerce.product;

import java.math.BigDecimal;


public record ProductResponse(
    Integer id,
    String name,
    String description,
    double availableQuantity,
    BigDecimal price
) {

}
