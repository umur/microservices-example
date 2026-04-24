# CinéTrack — Microservices with Spring Boot 4

Companion code for the book **Microservices with Spring Boot 4** by Umur Inan. CinéTrack is a film-tracking platform built as a production-grade microservices system: users register, browse a catalog sourced from TMDB, maintain watchlists, post reviews, and receive personalised recommendations.

Every chapter adds one concept on top of the previous one. The `final/` directory is the complete system; each `chapter-NN/` directory is a self-contained snapshot of the codebase at the end of that chapter.

## Quickstart

```bash
git clone https://github.com/umur/microservices-example
cd microservices-example/final
docker compose up -d
```

The API gateway comes up on `http://localhost:8080`, Eureka on `http://localhost:8761`, Mailhog on `http://localhost:8025`.

## Tech Stack

| Layer | Choice |
|------|--------|
| Runtime | Java 21 |
| Framework | Spring Boot 4.0.5, Spring Cloud 2025.1.1 |
| Build | Maven (multi-module) |
| Messaging | Apache Kafka 4.x (KRaft mode) |
| Datastores | PostgreSQL 16, Redis 7, Elasticsearch 8 |
| Observability | Micrometer, Prometheus, OpenTelemetry, Grafana |
| Testing | JUnit 5, Mockito, AssertJ, Testcontainers, Spring Cloud Contract |
| Security | Spring Authorization Server 7, OAuth2 Resource Server, JWT |
| Containers | Docker, Kubernetes, Istio |

## Services

| Service | Port | Responsibility |
|---------|------|----------------|
| `eureka-server` | 8761 | Service registry |
| `config-server` | 8888 | Centralised configuration |
| `api-gateway` | 8080 | Single entry point, routing, rate limiting |
| `user-service` | 8082 | OAuth2 authorization server, user accounts |
| `catalog-service` | 8081 | Movie metadata, TMDB integration |
| `watchlist-service` | 8083 | User watchlists |
| `review-service` | 8084 | Reviews + transactional outbox |
| `recommendation-service` | 8086 | Redis-backed taste profiles (CQRS) |
| `notification-service` | 8085 | Email via Mailhog |
| `search-service` | 8087 | Reactive search (WebFlux + R2DBC) |

## Repository Layout

```
.
├── pom.xml                 ← parent POM (BOM + JaCoCo + plugin management)
├── cinetrack-common/       ← shared DTOs, exceptions, domain events
├── cinetrack-modulith/     ← Spring Modulith demo (Chapter 21)
├── <service>/              ← each microservice as a Maven module
├── chapter-01/ … chapter-34/  ← cumulative snapshots of the whole system
├── final/                  ← mirror of chapter-34 — the complete system
├── docker-compose.yml      ← infrastructure + services
└── k8s/                    ← Kubernetes manifests
```

## Running Locally

### Full stack via Docker Compose

```bash
docker compose up -d
docker compose logs -f api-gateway
```

### A single service against live infra

```bash
# bring up just infra
docker compose up -d postgres-catalog postgres-users postgres-watchlist postgres-reviews kafka redis mailhog eureka-server

# run one service from source
./mvnw -pl catalog-service spring-boot:run
```

### Running tests

```bash
# unit tests only (fast, no Docker)
./mvnw test

# unit + integration tests (requires Docker)
./mvnw verify
```

The parent POM enforces **80% combined unit + integration coverage** across every module via JaCoCo.

## Chapter Snapshots

Each `chapter-NN/` is a complete, buildable copy of the whole system as it stood at the end of that chapter. Use them to jump into a specific topic without running through the preceding chapters:

| Chapter | Focus |
|--------:|-------|
| 03 | First Spring Boot 4 service (catalog-service) |
| 04 | REST + gRPC inter-service communication |
| 05 | Eureka service discovery |
| 06 | Spring Cloud Gateway |
| 07 | Spring Cloud Config Server |
| 08 | Resilience4j: circuit breaker, retry, bulkhead |
| 09 | Observability: metrics, tracing, structured logs |
| 10 | Database per service + Flyway |
| 11 | Eventual consistency with Kafka |
| 12 | Transactional outbox + notification service |
| 13 | Saga orchestration |
| 14 | CQRS + recommendation-service |
| 15–16 | OAuth2 Authorization Server + service-to-service JWT |
| 17 | Spring Cloud Contract tests |
| 18 | Docker + multi-stage builds |
| 19–20 | Kubernetes + GitOps |
| 21 | Spring Modulith (modular monolith) |
| 22 | Strangler fig migration |
| 23 | API versioning |
| 24 | Performance: k6, HikariCP, JVM tuning |
| 25 | GraalVM native image |
| 26 | Event sourcing |
| 27 | Avro + Schema Registry |
| 28 | gRPC streaming |
| 29 | Istio service mesh |
| 30 | Multi-tenancy |
| 31 | Redisson distributed locking |
| 32 | WebFlux + R2DBC (reactive) |
| 33 | Multi-region with MirrorMaker2 |
| 34 | Chaos engineering |

## Contributing

Issues and pull requests are welcome — especially if you spot a mismatch between the book and the code, or a way to make the examples clearer.

## Licence

MIT. See `LICENSE`.

## Book

[Microservices with Spring Boot 4](https://github.com/umur/microservices-example) — full book, including prose, diagrams, and these examples.
