# Microservices with Spring Boot 4

> Building a real microservices system end-to-end: from the first service to a production cluster.

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0-6DB33F?logo=spring&logoColor=white) ![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-2025.x-6DB33F?logo=spring&logoColor=white) ![License: MIT](https://img.shields.io/badge/License%3A_MIT-MIT-blue)

Companion code for **Microservices with Spring Boot 4** by [Umur Inan](https://umurinan.com).

## About the book

CinéTrack is a film-tracking platform built as a production-grade microservices system: users register, browse a catalog sourced from TMDB, maintain watchlists, post reviews, and receive personalised recommendations. Across 34 chapters the system grows from a single Spring Boot service to a multi-service architecture with API gateway, service discovery, externalized config, distributed tracing, circuit breakers, event-driven workflows, and the operational maturity to run all of it.

## Who this is for

- Engineers moving from a monolith to a distributed system for the first time
- Spring Boot developers who need service discovery, circuit breaking, and API gateway in production
- Teams making the architectural decision between microservices, modulith, and monolith

## Chapters

1. The Microservices Decision
2. Domain-Driven Design for Service Boundaries
3. Your First Spring Boot 4 Microservice
4. Service-to-Service Communication
5. Service Discovery and Load Balancing
6. API Gateway with Spring Cloud Gateway
7. Configuration Management
8. Resilience: Circuit Breakers and Bulkheads
9. Observability: Tracing, Metrics, Logs
10. Database per Service
11. Eventual Consistency in Practice
12. The Outbox Pattern
13. The Saga Pattern
14. CQRS at the Service Level
15. OAuth2 and JWT in Microservices
16. Service-to-Service Security
17. Testing Microservices
18. Containerizing Spring Microservices
19. Kubernetes for Spring Developers
20. Deployment Patterns
21. The Modular Monolith as Gateway
22. Migrating from Monolith to Microservices
23. API Versioning and Governance
24. Performance and Scalability
25. GraalVM Native and Spring AOT
26. Event Sourcing in Microservices
27. Schema Registry and Event Contracts
28. gRPC: Streaming and Advanced Patterns
29. Service Mesh with Istio
30. Multi-Tenancy in Microservices
31. Distributed Locking and Coordination
32. Reactive Microservices with WebFlux
33. Multi-Region and Global Deployment
34. Chaos Engineering

## Prerequisites

- Java 21 LTS ([Temurin](https://adoptium.net))
- Maven 3.9+
- Docker & Docker Compose (Postgres, Redis, Kafka, Elasticsearch)

## Quick start

```bash
git clone https://github.com/umur/microservices-example
cd microservices-example/final
docker compose up -d
```

To follow the book chapter by chapter, start at `chapter-01/` and progress forward.

## Layout

- `chapter-01/ ... chapter-34/`: cumulative system state at the end of each chapter
- `final/`: the complete system
- Top-level service modules: `api-gateway`, `catalog-service`, `user-service`, `watchlist-service`, `review-service`, `recommendation-service`, `notification-service`, `search-service`, plus `config-server`, `eureka-server`, and the shared `cinetrack-common` and `cinetrack-modulith` modules
- `cinetrack-config/`: externalized configuration served by `config-server` via Spring Cloud Config
- `k8s/`: baseline Kubernetes manifests for the final state

## Stack

- Java 21 (LTS)
- Spring Boot 4
- Spring Cloud (Config Server, Gateway, OpenFeign, Resilience4j)
- Eureka for service discovery
- PostgreSQL, Redis, Kafka, Elasticsearch
- Docker Compose for local orchestration
- Kubernetes for production deployment

## Related books

- [Cloud-Native Spring Boot on Kubernetes](https://github.com/umur/kubernetes-example): the sequel: takes the CineTrack system built here and operates it on Kubernetes
- [Event-Driven Architecture with Spring Boot 4 and Kafka 4](https://github.com/umur/event-driven-architecture-book-examples): deeper treatment of Kafka patterns introduced in Chapters 11 to 14
- [Spring Security 7: From Internals to Production](https://github.com/umur/spring-security-book-example): deeper treatment of the OAuth2 and JWT patterns in Chapters 15 and 16

## About the author

I'm Umur Inan. I write production-focused books about Java, Spring Boot, distributed systems, and everything that makes software reliable at scale.

[umurinan.com](https://umurinan.com)

## License

MIT. See [LICENSE](LICENSE).
