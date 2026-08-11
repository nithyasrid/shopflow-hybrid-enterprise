package com.shopflow.entity;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="cart_items") @Getter @Setter @NoArgsConstructor
public class CartItem { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @Column(name="cart_id") Long cartId; @Column(name="product_id") Long productId; int quantity; }
