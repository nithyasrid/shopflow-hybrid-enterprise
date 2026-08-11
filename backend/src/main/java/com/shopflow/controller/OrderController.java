package com.shopflow.controller;
import com.shopflow.dto.CommerceDtos.CheckoutRequest; import com.shopflow.entity.Order; import com.shopflow.repository.OrderRepository; import com.shopflow.service.OrderService; import jakarta.validation.Valid; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/orders")
public class OrderController {
 private final OrderService service; private final OrderRepository repo; public OrderController(OrderService s,OrderRepository r){service=s;repo=r;}
 private Long uid(Authentication a){return ((io.jsonwebtoken.Claims)a.getDetails()).get("uid",Long.class);}
 @PostMapping("/checkout") public Object checkout(Authentication a,@Valid @RequestBody CheckoutRequest x){return service.checkout(uid(a),x);}
 @GetMapping public List<Order> all(Authentication a){return repo.findAll().stream().filter(o->o.getUserId().equals(uid(a))).toList();}
 @GetMapping("/{id}") public Order one(Authentication a,@PathVariable Long id){Order o=repo.findById(id).orElseThrow();if(!o.getUserId().equals(uid(a))&&!a.getAuthorities().stream().anyMatch(x->x.getAuthority().equals("ROLE_ADMIN")))throw new org.springframework.security.access.AccessDeniedException("Forbidden");return o;}
}
