CREATE SCHEMA IF NOT EXISTS `shopflow_dw`;
CREATE TABLE IF NOT EXISTS `shopflow_dw.fact_orders` (
  order_id INT64,user_id INT64,subtotal NUMERIC,discount NUMERIC,tax NUMERIC,shipping NUMERIC,total_amount NUMERIC,status STRING,created_at TIMESTAMP
);
CREATE TABLE IF NOT EXISTS `shopflow_dw.fact_events` (
  event_id STRING,event_type STRING,user_id INT64,order_id INT64,amount NUMERIC,payload JSON,event_timestamp TIMESTAMP
);
CREATE TABLE IF NOT EXISTS `shopflow_dw.dim_product` (
  product_id INT64,name STRING,description STRING,price NUMERIC,stock INT64,category_id INT64,brand_id INT64,active BOOL,created_at TIMESTAMP
);
