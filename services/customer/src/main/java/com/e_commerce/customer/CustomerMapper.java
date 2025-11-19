package com.e_commerce.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public Customer toCustomer(CustomerRequest request) {
        if (request == null) {
            return null;
        }


        return Customer.builder()
                .id(request.id())
                .firstname(request.firstName())
                .lastname(request.lastName())
                .address(request.address())
                .email(request.email())
                .build();
    }

    public CustomerResponse toCustomerResponse(Customer customer)
    {
        if (customer == null) {
            return null;
        }

        return new CustomerResponse(
            customer.getId(),
            customer.getFirstname(),
            customer.getLastname(),
            customer.getEmail(),
            customer.getAddress()
        );
    }
}
