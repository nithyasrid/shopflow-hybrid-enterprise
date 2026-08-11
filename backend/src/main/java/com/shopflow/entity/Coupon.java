package com.shopflow.entity;
import jakarta.persistence.*; import lombok.*; import java.math.BigDecimal; import java.time.OffsetDateTime;
@Entity @Table(name="coupons") @Getter @Setter @NoArgsConstructor
public class Coupon { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @Column(unique=true) String code; @Column(name="discount_type") String discountType; @Column(name="discount_value") BigDecimal discountValue; @Column(name="minimum_cart_value") BigDecimal minimumCartValue; @Column(name="usage_limit") int usageLimit; @Column(name="used_count") int usedCount; @Column(name="expires_at") OffsetDateTime expiresAt; }
