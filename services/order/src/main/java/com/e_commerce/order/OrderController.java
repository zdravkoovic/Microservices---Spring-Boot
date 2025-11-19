package com.e_commerce.order;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    
    public final OrderService service;

    @PostMapping
    public ResponseEntity<OrderResponse> create(
        @RequestBody @Valid OrderRequest request
    ) {
        return ResponseEntity.ok(this.service.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> show()
    {
        return ResponseEntity.ok(this.service.getAllOrders());
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> showById(@PathVariable("order-id") Integer id) {
        return ResponseEntity.ok(this.service.getOrderById(id));
    }
    
}
