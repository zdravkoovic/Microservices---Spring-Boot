package com.e_commerce.product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.e_commerce.errors.ProductPurchaseException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    public final ProductRepository repository;
    public final ProductMapper mapper;

    public ProductResponse createProduct(ProductRequest request) {
        
        var product = mapper.toProduct(request);

        product = this.repository.save(product);

        return mapper.toProductRequest(product);
    }

    @Transactional(rollbackOn = ProductPurchaseException.class)
    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = request
                    .stream()
                    .map(ProductPurchaseRequest::productId)
                    .toList();

        var storedProducts = this.repository.findAllByIdInOrderById(productIds);
        if(storedProducts.size() != productIds.size()) {
            throw new ProductPurchaseException("One or more products not found");
        }

        var storedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for(int i = 0; i < storedProducts.size(); i++){
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);

            if(product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: $s" + productRequest.productId());
            }

            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            this.repository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }

        return purchasedProducts;
    }

    public ProductResponse getProductById(Integer id) {
        return this.repository.findById(id)
                    .map(mapper::toProductRequest)
                    .orElseThrow(() -> new EntityNotFoundException("Cannot find product with id: " + id));
        
    }

    public List<ProductResponse> getAllProducts() {
        return this.repository.findAll()
            .stream()
            .map(mapper::toProductRequest)
            .collect(Collectors.toList());
    }

}
