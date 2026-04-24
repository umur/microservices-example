package com.cinetrack.notification.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationService.class);

    public void sendReviewPostedNotification(String payload) {
        log.info("Sending review-posted notification. Payload: {}", payload);
        // In production: send via SMTP/Mailhog
    }
}
