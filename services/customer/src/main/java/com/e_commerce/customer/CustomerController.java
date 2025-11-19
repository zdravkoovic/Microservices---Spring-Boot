package com.e_commerce.customer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    
    private final CustomerService service;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(service.createCustomer(request));
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(
        @RequestBody @Valid CustomerRequest request
    ) {
        service.updateCustomer(request);

        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        var customers = service.getAllCustomers();

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> existsById(
        @PathVariable("customer-id") String customerId
    ) {
        return ResponseEntity.ok(this.service.existsById(customerId));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable("customer-id") String id) {
        var customer = service.findById(id);

        return ResponseEntity.ok(customer);
    }
    
    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> delete(
        @PathVariable("customer-id") String id
    ){
        service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
    
}
