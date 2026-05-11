# Microservices with Spring Boot 4

> Building a real microservices system end-to-end — from the first service to a production cluster.

Companion code for the book **Microservices with Spring Boot 4** by [Umur Inan](https://umurinan.com).

## About the book

CinéTrack is a film-tracking platform built as a production-grade microservices system: users register, browse a catalog sourced from TMDB, maintain watchlists, post reviews, and receive personalised recommendations. Across 34 chapters the system grows from a single Spring Boot service to a multi-service architecture with API gateway, service discovery, externalized config, distributed tracing, circuit breakers, event-driven workflows, and the operational maturity to run all of it.

## Quick start

```bash
git clone https://github.com/umur/microservices-example
cd microservices-example/final
docker compose up -d
```

To follow the book chapter by chapter, start at `chapter-01/` and progress forward.

## Layout

- `chapter-01/ … chapter-34/` — cumulative system state at the end of each chapter
- `final/` — the complete system
- Top-level service modules — `api-gateway`, `catalog-service`, `user-service`, `watchlist-service`, `review-service`, `recommendation-service`, `notification-service`, `search-service`, plus `config-server`, `eureka-server`, and the shared `cinetrack-common` and `cinetrack-modulith` modules
- `cinetrack-config/` — externalized configuration served by `config-server` via Spring Cloud Config
- `k8s/` — baseline Kubernetes manifests for the final state

## Stack

- Java 21 (LTS)
- Spring Boot 4
- Spring Cloud (Config Server, Gateway, OpenFeign, Resilience4j)
- Eureka for service discovery
- PostgreSQL, Redis, Kafka, Elasticsearch
- Docker Compose for local orchestration
- Kubernetes for production deployment

## About the author

I'm Umur Inan. I write books about Spring Boot, Java, distributed systems, and the practices that make production reliable.

📚 **More writing and books → [umurinan.com](https://umurinan.com)**

## License

MIT — see [LICENSE](LICENSE).
