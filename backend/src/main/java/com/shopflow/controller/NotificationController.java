package com.shopflow.controller;
import com.shopflow.repository.NotificationRepository; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/notifications")
public class NotificationController {
 private final NotificationRepository repo; public NotificationController(NotificationRepository r){repo=r;}
 @GetMapping public Object list(Authentication a){var c=(io.jsonwebtoken.Claims)a.getDetails();return repo.findByUserIdOrderByCreatedAtDesc(c.get("uid",Long.class));}
}
