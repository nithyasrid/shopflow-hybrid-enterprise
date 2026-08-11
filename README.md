# 🛍️ ShopFlow — Enterprise E-Commerce & Data Intelligence Platform

> Production-inspired hybrid project for **Java Backend/SDE + Data Engineering** fresher applications.

## What this project demonstrates

### Java Backend / SDE
- Java 21
- Spring Boot 3.x
- REST APIs
- Spring Security + JWT access/refresh tokens
- Role-based authorization
- Spring Data JPA / Hibernate
- PostgreSQL
- Bean Validation
- Global exception handling
- Product/category/brand management
- Inventory management
- Shopping cart
- Checkout/order workflow
- Mock payments/refunds
- Coupons
- Reviews and ratings
- Notifications
- Invoice API
- Audit logging
- Analytics APIs
- Kafka event publishing
- Docker

### Data Engineering
- Kafka event streaming
- Python event consumer
- PySpark batch transformation
- Automated data-quality rules
- Airflow orchestration
- BigQuery-ready warehouse SQL
- Streamlit analytics dashboard

## Architecture

```text
                    SHOPFLOW
                       |
             +---------+---------+
             |                   |
       JAVA BACKEND          DATA PLATFORM
             |                   |
      Spring Boot 3.x          Kafka
      Spring Security          Python
      JWT                      PySpark
      REST APIs                Data Quality
      JPA/Hibernate            Airflow
      PostgreSQL               BigQuery
             |                   |
             +---------+---------+
                       |
                    Docker
                       |
                    GitHub
```

## Runtime event flow

```text
Client / Swagger
      |
      v
Spring Boot REST API
      |
      +----> PostgreSQL (transactions)
      |
      +----> Kafka
               |
               +--> orders
               +--> payments
               +--> inventory
               +--> users
                      |
                      v
              Python processor
                      |
                      v
                Data Quality
                      |
                      v
             analytics_event table
                      |
                      v
                 Dashboard

Airflow periodically reconciles transactional data and records pipeline quality.
BigQuery SQL is included as the cloud warehouse target.
```

## Quick start

### Prerequisites
- Docker Desktop
- Git
- Optional: Java 21 + Maven for running the backend outside Docker

### Start

```bash
docker compose up --build -d
docker compose ps
```

Open:

- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui/index.html
- Dashboard: http://localhost:8501
- Airflow: http://localhost:8088

Airflow:
`admin / admin`

### Register

`POST /api/auth/register`

```json
{
  "name": "Nithu",
  "email": "nithu@example.com",
  "password": "Password@123",
  "role": "CUSTOMER"
}
```

### Login

`POST /api/auth/login`

```json
{
  "email": "nithu@example.com",
  "password": "Password@123"
}
```

Copy the returned access token and use:

```text
Swagger → Authorize → Bearer <token>
```

### Admin user

For local demo, the seed script creates:

```text
email: admin@shopflow.local
password: Admin@123
role: ADMIN
```

Use it to test protected admin product/inventory endpoints.

## Demo order

1. Login as admin.
2. Create/list products.
3. Register a customer.
4. Login as customer.
5. Add product to cart.
6. Apply coupon `WELCOME10`.
7. Checkout.
8. Mock payment is created.
9. Order/payment/inventory events are published to Kafka.
10. Python processor validates order events and updates analytics.
11. Open Streamlit dashboard.
12. Open Airflow and trigger `shopflow_batch_reconciliation`.

## Important project honesty

This is **production-inspired**, not a production payment system. Payments are mocked, JWT signing uses an environment secret, and BigQuery is a ready target rather than required for the local zero-cost demo.

The default Docker stack uses a lightweight Python Kafka consumer so the project remains practical on a student laptop. A PySpark batch implementation is included separately and can be executed with `spark-submit`.

## GitHub

```bash
git init
git add .
git commit -m "Build ShopFlow hybrid enterprise platform"
git branch -M main
git remote add origin YOUR_GITHUB_REPO_URL
git push -u origin main
```

Never commit `.env` or GCP credentials.
