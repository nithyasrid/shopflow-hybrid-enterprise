package com.shopflow.entity;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="brands") @Getter @Setter @NoArgsConstructor
public class Brand { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @Column(unique=true,nullable=false) String name; }
