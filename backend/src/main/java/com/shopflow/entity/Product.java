package com.shopflow.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
@Entity @Table(name="products")
@Getter @Setter @NoArgsConstructor
public class Product {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 private String description;
 @Column(nullable=false) private BigDecimal price;
 @Column(nullable=false) private Integer stock;
 @Column(name="category_id") private Long categoryId;
 @Column(name="brand_id") private Long brandId;
 @Column(nullable=false) private boolean active=true;
 @Column(name="created_at") private OffsetDateTime createdAt;
}
