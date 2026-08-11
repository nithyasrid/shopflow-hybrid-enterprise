package com.shopflow.repository;
import com.shopflow.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import 
public interface CartRepository extends JpaRepository<Cart,Long> { Optional<Cart> findByUserId(Long userId); }
