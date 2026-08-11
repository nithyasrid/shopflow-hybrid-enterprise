package com.shopflow.entity;
import jakarta.persistence.*; import lombok.*; import java.math.BigDecimal;
@Entity @Table(name="order_items") @Getter @Setter @NoArgsConstructor
public class OrderItem { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @Column(name="order_id") Long orderId; @Column(name="product_id") Long productId; int quantity; @Column(name="unit_price") BigDecimal unitPrice; }
