package com.e_commerce.kafka.order;

public record Customer(
    String id,
    String firstname,
    String lastname,
    String email
) {}
