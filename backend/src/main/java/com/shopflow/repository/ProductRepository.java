package com.shopflow.repository;
import com.shopflow.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import 
public interface ProductRepository extends JpaRepository<Product,Long> {  }
