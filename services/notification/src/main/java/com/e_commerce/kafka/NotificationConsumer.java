package com.e_commerce.kafka;

import static java.lang.String.format;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.e_commerce.email.EmailService;
import com.e_commerce.kafka.order.OrderConfirmation;
import com.e_commerce.kafka.payment.PaymentConfirmation;
import com.e_commerce.notification.Notification;
import com.e_commerce.notification.NotificationRepository;

import jakarta.mail.MessagingException;

import static com.e_commerce.notification.NotificationType.ORDER_CONFIRMATION;
import static com.e_commerce.notification.NotificationType.PAYMENT_CONFIRMATION;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    
    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotifications(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info(format("Consuming the message from payment-topic Topic:: %s", paymentConfirmation));

        repository.save(
            Notification.builder()
                        .type(PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        var customerName = paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname();

        emailService.sendPaymentSuccessEmail(
            paymentConfirmation.customerEmail(), 
            customerName, 
            paymentConfirmation.amount(), 
            paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderSuccessNotifications(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(format("Consuming the message from payment-topic Topic:: %s", orderConfirmation));

        repository.save(
            Notification.builder()
                        .type(ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        var customerName = orderConfirmation.customer().firstname() + " " + orderConfirmation.customer().lastname();

        emailService.sendOrderConfirmationEmail(
            orderConfirmation.customer().email(), 
            customerName, 
            orderConfirmation.totalAmount(), 
            orderConfirmation.orderReference(), 
            orderConfirmation.products()
        );

    }
}
