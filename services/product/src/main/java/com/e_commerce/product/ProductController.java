package com.e_commerce.product;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    
    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponse> create(
        @RequestBody @Valid ProductRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.createProduct(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(
        @RequestBody List<ProductPurchaseRequest> request
    ) { 
        return ResponseEntity.ok(this.service.purchaseProducts(request));
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> getProductById(
        @PathVariable("product-id") Integer id
    ) {
        return ResponseEntity.ok(this.service.getProductById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(this.service.getAllProducts());
    }
    
}
