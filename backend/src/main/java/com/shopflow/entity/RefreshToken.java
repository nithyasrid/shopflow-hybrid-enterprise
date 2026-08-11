package com.shopflow.entity;
import jakarta.persistence.*; import lombok.*; import java.time.OffsetDateTime;
@Entity @Table(name="refresh_tokens") @Getter @Setter @NoArgsConstructor
public class RefreshToken { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @Column(name="user_id") Long userId; @Column(unique=true) String token; @Column(name="expires_at") OffsetDateTime expiresAt; }
