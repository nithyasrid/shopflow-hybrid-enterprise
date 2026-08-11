package com.shopflow.entity;
import jakarta.persistence.*; import lombok.*;
@Entity @Table(name="categories") @Getter @Setter @NoArgsConstructor
public class Category { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @Column(unique=true,nullable=false) String name; }
