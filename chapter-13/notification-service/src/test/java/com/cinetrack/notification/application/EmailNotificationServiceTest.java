package com.cinetrack.notification.application;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class EmailNotificationServiceTest {

    @Test
    void sendReviewPostedNotification_doesNotThrow() {
        var service = new EmailNotificationService();
        assertThatCode(() -> service.sendReviewPostedNotification("{\"id\":\"test\"}"))
                .doesNotThrowAnyException();
    }
}
