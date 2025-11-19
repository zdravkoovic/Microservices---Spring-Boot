package com.e_commerce.customer;

import org.springframework.stereotype.Service;

import com.e_commerce.errors.CustomerNotFoundException;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import static java.lang.String.format;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));

        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        System.out.println("Request ID: '" + request.id() + "'");
        var customer = repository.findById(request.id())
            .orElseThrow(() -> new CustomerNotFoundException(format("Cannot update customer:: No customer found with the previous ID:: %s", request.id())));

        mergerCustomer(customer, request);
        repository.save(customer);
    }

    public void mergerCustomer(Customer customer, CustomerRequest request) {
        if(StringUtils.isNotBlank(request.firstName())) {
            customer.setFirstname(request.firstName());
        }
        if(StringUtils.isNotBlank(request.lastName())) {
            customer.setLastname(request.lastName());
        }
        if(StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if(request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> getAllCustomers() {

        return repository.findAll()
            .stream()
            .map(mapper::toCustomerResponse)
            .toList();
    }

    public boolean existsById(String customerId) {
        
        return this.repository.findById(customerId).isPresent(); 
    }

    public CustomerResponse findById(String id) {

        return repository.findById(id)
                .map(mapper::toCustomerResponse)
                .orElseThrow(() -> new CustomerNotFoundException(format("No customer found with the provided ID:: %s", id)));
    }

    public void deleteCustomer(String id) {
        var customer = repository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException(format("Cannot delete customer:: No customer found with the provided ID:: %s", id)));  
        repository.delete(customer);
    }

}
 