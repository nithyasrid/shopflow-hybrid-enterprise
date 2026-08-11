package com.shopflow.repository;
import com.shopflow.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import 
public interface CartItemRepository extends JpaRepository<CartItem,Long> { java.util.List<CartItem> findByCartId(Long cartId); }
