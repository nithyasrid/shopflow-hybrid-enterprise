package com.shopflow.entity;
import jakarta.persistence.*; import lombok.*; import java.time.OffsetDateTime;
@Entity @Table(name="audit_logs") @Getter @Setter @NoArgsConstructor
public class AuditLog { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id; @Column(name="user_id") Long userId; String action; @Column(name="entity_type") String entityType; @Column(name="entity_id") String entityId; String details; @Column(name="created_at") OffsetDateTime createdAt; }
