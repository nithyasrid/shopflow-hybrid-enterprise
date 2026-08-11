# 7-minute interview demo

1. `docker compose up --build -d`
2. Open Swagger.
3. Register/login.
4. Authorize with the JWT access token.
5. Add a product to the cart.
6. Checkout with coupon `WELCOME10`.
7. Show PostgreSQL order/payment/inventory changes.
8. Run `docker compose logs -f stream-processor`.
9. Show Kafka-derived analytics in Streamlit.
10. Open Airflow and trigger `shopflow_batch_reconciliation`.
11. Explain BigQuery as the cloud warehouse target.

## Java story

Spring Boot → Security/JWT → Controller → Service → Repository/JPA → PostgreSQL → Kafka.

## DE story

Kafka → Python/PySpark → data quality → Airflow → BigQuery → analytics.

Never claim a feature that you have not demonstrated.
