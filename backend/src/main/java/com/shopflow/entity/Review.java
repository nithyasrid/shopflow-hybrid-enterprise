package com.shopflow.entity;
import jakarta.persistence.*; import lombok.*; import java.time.OffsetDateTime;
@Entity @Table(name="reviews") @Getter @Setter @NoArgsConstructor
public class Review { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @Column(name="user_id") Long userId; @Column(name="product_id") Long productId; int rating; String comment; @Column(name="created_at") OffsetDateTime createdAt; }
