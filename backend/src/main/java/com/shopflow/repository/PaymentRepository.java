package com.shopflow.repository;
import com.shopflow.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import 
public interface PaymentRepository extends JpaRepository<Payment,Long> {  }
