package com.shopflow.entity;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="carts") @Getter @Setter @NoArgsConstructor
public class Cart { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @Column(name="user_id",unique=true,nullable=false) Long userId; }
