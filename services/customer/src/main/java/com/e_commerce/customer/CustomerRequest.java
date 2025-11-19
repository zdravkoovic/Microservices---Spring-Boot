package com.e_commerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
    String id,
    @NotNull(message = "firstName is required")
    String firstName,
    @NotNull(message = "lastName is required")
    String lastName,
    @NotNull(message = "email is required")
    @Email(message = "email should be valid")
    String email,
    Address address
) {

}
