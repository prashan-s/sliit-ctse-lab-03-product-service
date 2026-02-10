# Product Service

Simple Spring Boot microservice for managing products with REST APIs, in-memory H2 persistence, and Swagger UI documentation.

## Features
- CRUD endpoints at `/api/products`
- H2 in-memory database (console at `/h2-console`)
- OpenAPI/Swagger UI at `/swagger-ui.html`
- DTO-based validation and global error handling

## Tech Stack
- Spring Boot 3.2.x
- Spring Web, Spring Data JPA, Validation
- H2 Database
- Springdoc OpenAPI

## Running
```bash
MAVEN_USER_HOME=/tmp/m2 ./mvnw -Dmaven.repo.local=/tmp/m2/repo spring-boot:run
```
Then open:
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:productdb`, user `sa`, no password)

## Testing
```bash
MAVEN_USER_HOME=/tmp/m2 ./mvnw -Dmaven.repo.local=/tmp/m2/repo test
```

## API Overview
- `POST /api/products` – create product
- `GET /api/products` – list all
- `GET /api/products/{id}` – get by id
- `DELETE /api/products/{id}` – delete

## Swagger UI Preview
![Swagger UI](/Users/prashansamarathunge/Personal/SLIIT/product-service/assets/swagger-ss.png)
