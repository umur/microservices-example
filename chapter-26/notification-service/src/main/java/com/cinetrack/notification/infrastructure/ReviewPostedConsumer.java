package com.cinetrack.notification.infrastructure;

import com.cinetrack.notification.application.EmailNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ReviewPostedConsumer {

    private static final Logger log = LoggerFactory.getLogger(ReviewPostedConsumer.class);

    private final EmailNotificationService emailService;

    public ReviewPostedConsumer(EmailNotificationService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "review.posted", groupId = "${spring.kafka.consumer.group-id}")
    public void onReviewPosted(String message) {
        log.info("Received review.posted event");
        emailService.sendReviewPostedNotification(message);
    }
}
