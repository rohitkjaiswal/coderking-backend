# CoderKing - Backend

Spring Boot scaffold for the CoderKing platform.

## Quick start

1. Java 17+ and Maven required.
2. Configure MySQL and update `src/main/resources/application.properties`.
3. Build & run:
   mvn clean package
   mvn spring-boot:run

Endpoints (sample):
- POST /api/auth/register
- POST /api/auth/login
- GET /api/contests
