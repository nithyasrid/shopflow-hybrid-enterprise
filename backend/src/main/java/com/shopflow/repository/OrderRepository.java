package com.shopflow.repository;
import com.shopflow.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import 
public interface OrderRepository extends JpaRepository<Order,Long> {  }
