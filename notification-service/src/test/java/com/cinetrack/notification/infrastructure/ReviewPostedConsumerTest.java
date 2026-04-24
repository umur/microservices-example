package com.cinetrack.notification.infrastructure;

import com.cinetrack.notification.application.EmailNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReviewPostedConsumerTest {

    @Mock
    EmailNotificationService emailService;

    @InjectMocks
    ReviewPostedConsumer consumer;

    @Test
    void onReviewPosted_delegatesToEmailService() {
        String payload = "{\"reviewId\":\"r1\",\"rating\":5}";

        consumer.onReviewPosted(payload);

        verify(emailService).sendReviewPostedNotification(payload);
    }

    @Test
    void onReviewPosted_withDifferentPayload_delegatesCorrectly() {
        String payload = "{\"reviewId\":\"r2\",\"rating\":3}";

        consumer.onReviewPosted(payload);

        verify(emailService).sendReviewPostedNotification(payload);
    }
}
