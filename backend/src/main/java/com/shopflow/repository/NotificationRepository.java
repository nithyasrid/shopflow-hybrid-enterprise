package com.shopflow.repository;
import com.shopflow.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import 
public interface NotificationRepository extends JpaRepository<Notification,Long> { java.util.List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId); }
