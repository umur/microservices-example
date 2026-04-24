package contracts.review

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should return reviews for a movie"
    request {
        method GET()
        url "/api/reviews/movies/550e8400-e29b-41d4-a716-446655440000"
        headers {
            accept applicationJson()
        }
    }
    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body([
            [
                id: "123e4567-e89b-12d3-a456-426614174000",
                movieId: "550e8400-e29b-41d4-a716-446655440000",
                rating: 5,
                body: "Excellent film"
            ]
        ])
    }
}
