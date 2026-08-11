package com.shopflow.entity;
import jakarta.persistence.*; import lombok.*; import java.math.BigDecimal; import java.time.OffsetDateTime;
@Entity @Table(name="orders") @Getter @Setter @NoArgsConstructor
public class Order {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
 @Column(name="user_id") Long userId;
 BigDecimal subtotal,discount,tax,shipping;
 @Column(name="total_amount") BigDecimal totalAmount;
 String status;
 @Column(name="created_at") OffsetDateTime createdAt;
}
