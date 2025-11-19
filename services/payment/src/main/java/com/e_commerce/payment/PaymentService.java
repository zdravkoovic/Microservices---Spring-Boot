package com.e_commerce.payment;

import org.springframework.stereotype.Service;

import com.e_commerce.notification.NotificationProducer;
import com.e_commerce.notification.PaymentNotificationRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest request) {
        var payment = mapper.toPayment(request);
        payment = repository.save(payment);

        notificationProducer.sendNotification(
            new PaymentNotificationRequest(
                request.orderReference(),
                request.amount(),
                request.paymentMethod(),
                request.customer().firstname(),
                request.customer().lastname(),
                request.customer().email()
            )
        );

        return payment.getId();
    }

}
