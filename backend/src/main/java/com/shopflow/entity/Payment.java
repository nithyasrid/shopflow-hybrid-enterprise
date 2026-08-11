package com.shopflow.entity;
import jakarta.persistence.*; import lombok.*; import java.math.BigDecimal; import java.time.OffsetDateTime;
@Entity @Table(name="payments") @Getter @Setter @NoArgsConstructor
public class Payment { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @Column(name="order_id") Long orderId; BigDecimal amount; String method,status; @Column(name="created_at") OffsetDateTime createdAt; }
