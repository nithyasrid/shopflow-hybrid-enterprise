package com.shopflow.entity;
import jakarta.persistence.*; import lombok.*; import java.time.OffsetDateTime;
@Entity @Table(name="notifications") @Getter @Setter @NoArgsConstructor
public class Notification { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @Column(name="user_id") Long userId; String type,message; boolean read; @Column(name="created_at") OffsetDateTime createdAt; }
