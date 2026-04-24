package com.cinetrack.review.contract;

import com.cinetrack.review.application.ReviewResponse;
import com.cinetrack.review.application.ReviewService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
public class ReviewContractBase {

    @Autowired
    private com.cinetrack.review.api.ReviewController reviewController;

    @MockitoBean
    private ReviewService reviewService;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.standaloneSetup(reviewController);

        UUID movieId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        var response = new ReviewResponse(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                UUID.randomUUID(),
                movieId,
                5,
                "Excellent film",
                Instant.now()
        );
        when(reviewService.getReviewsForMovie(any())).thenReturn(List.of(response));
    }
}
