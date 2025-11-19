package com.e_commerce.order;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.e_commerce.customer.CustomerClient;
import com.e_commerce.errors.BusinessException;
import com.e_commerce.kafka.OrderConfirmation;
import com.e_commerce.kafka.OrderProducer;
import com.e_commerce.orderline.OrderLineRequest;
import com.e_commerce.orderline.OrderLineService;
import com.e_commerce.payment.PaymentClient;
import com.e_commerce.payment.PaymentRequest;
import com.e_commerce.product.ProductClient;
import com.e_commerce.product.PurchaseRequest;
import com.e_commerce.product.PurchaseResponse;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper mapper;
    private final OrderRpository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient; 
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public OrderResponse createOrder(OrderRequest request) {
        
        var customer = this.customerClient.findCustomerById(request.customerId())
                            .orElseThrow(() -> new BusinessException("Cannot create order: No customer exists with the provided ID"));
        
        
        var purchasedProducts = this.productClient.purchaseProducts(request.products());

        var order = this.repository.save(mapper.toOrder(request));

        for(PurchaseRequest purchaseRequest: request.products())
        {
            orderLineService.saveOrderLine(
                new OrderLineRequest(
                    null,
                    order.getId(),
                    purchaseRequest.productId(),
                    purchaseRequest.quantity()
                )
            );
        }

        var paymenyRequest = new PaymentRequest(
            request.totalAmount(), 
            request.paymentMethod(), 
            order.getId(), 
            order.getReference(), 
            customer
        );

        paymentClient.requestOrderPayment(paymenyRequest);

        purchasedProducts = purchasedProducts.stream().map(product -> {
            double requestedQunatity = request.products()
                                            .stream()
                                            .filter(req -> req.productId().equals(product.productId()))
                                            .map(PurchaseRequest::quantity)
                                            .findFirst()
                                            .orElse(0.0);
            return mapper.toOrderProducer(product, requestedQunatity);
        }).toList();

        orderProducer.sendOrderConfirmation(
            new OrderConfirmation(
                request.reference(),
                purchasedProducts.stream().map(PurchaseResponse::price).reduce(BigDecimal.ZERO, BigDecimal::add),
                request.paymentMethod(),
                customer,
                purchasedProducts
            )
        );

        return mapper.toOrderResponse(order);
    }

    public List<OrderResponse> getAllOrders() {
        return this.repository.findAll()
                    .stream()
                    .map(this.mapper::toOrderResponse)
                    .toList();
    }

    public OrderResponse getOrderById(Integer id) {
        return repository.findById(id)
                    .map(mapper::toOrderResponse)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("No order with the provided ID:: %d", id)));
    }

}
