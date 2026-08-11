package com.shopflow.controller;
import com.shopflow.entity.*; import com.shopflow.repository.*; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/admin") @PreAuthorize("hasRole('ADMIN')")
public class AdminController {
 private final OrderRepository orders; private final PaymentRepository payments; private final UserRepository users; private final ProductRepository products;
 public AdminController(OrderRepository o,PaymentRepository p,UserRepository u,ProductRepository pr){orders=o;payments=p;users=u;products=pr;}
 @GetMapping("/analytics") public Map<String,Object> analytics(){return Map.of("users",users.count(),"products",products.count(),"orders",orders.count(),"payments",payments.count());}
 @GetMapping("/orders") public List<Order> orders(){return orders.findAll();}
}
