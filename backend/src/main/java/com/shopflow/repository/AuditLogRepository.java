package com.shopflow.repository;
import com.shopflow.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import 
public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {  }
