package com.e_commerce.product;

import org.springframework.stereotype.Service;

import com.e_commerce.category.Category;

@Service
public class ProductMapper {

    public Product toProduct(ProductRequest request) {
        
        return Product.builder()
                .name(request.name())
                .description(request.description())
                .availableQuantity(request.availableQuantity())
                .price(request.price())
                .category(
                    Category.builder()
                        .id(request.categoryId())
                        .build()   
                )
                .build();
    }

    public ProductResponse toProductRequest(Product request) {
        
        if(request == null) {
            return null;
        }

        return new ProductResponse(
            request.getId(),
            request.getName(),
            request.getDescription(),
            request.getAvailableQuantity(),
            request.getPrice()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity) {

        if(product == null || quantity < 1) return null;

        return new ProductPurchaseResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getAvailableQuantity()
        );
    }

}
