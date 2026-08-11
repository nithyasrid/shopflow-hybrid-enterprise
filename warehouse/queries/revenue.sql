SELECT DATE(created_at) order_date, COUNT(*) orders, SUM(total_amount) revenue, AVG(total_amount) aov
FROM `shopflow_dw.fact_orders`
WHERE status='CONFIRMED'
GROUP BY order_date ORDER BY order_date;
