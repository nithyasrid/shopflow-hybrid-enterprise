# Architecture decisions

## Transactional database
PostgreSQL is the system of record for users, products, carts, orders and payments.

## Event streaming
Kafka decouples transactional writes from analytics processing.

## Processing
The default laptop demo uses Python Kafka consumption. `streaming/spark/order_batch_job.py` is the Spark batch implementation.

## Orchestration
Airflow runs reconciliation and data-quality workflows.

## Warehouse
BigQuery SQL is included as the analytical target.

## UI
Streamlit is intentionally lightweight and is used only for analytics, not transactional operations.
