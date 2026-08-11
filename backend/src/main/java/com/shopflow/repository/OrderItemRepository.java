package com.shopflow.repository;
import com.shopflow.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import 
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> { java.util.List<OrderItem> findByOrderId(Long orderId); }
