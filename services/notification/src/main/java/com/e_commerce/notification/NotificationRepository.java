package com.e_commerce.notification;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface NotificationRepository extends MongoRepository<Notification, String> {


}