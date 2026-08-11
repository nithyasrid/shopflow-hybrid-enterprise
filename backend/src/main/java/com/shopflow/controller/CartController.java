package com.shopflow.controller;
import com.shopflow.dto.CommerceDtos.CartItemRequest; import com.shopflow.service.CartService; import jakarta.validation.Valid; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/cart")
public class CartController {
 private final CartService s; public CartController(CartService s){this.s=s;}
 private Long uid(Authentication a){return ((io.jsonwebtoken.Claims)a.getDetails()).get("uid",Long.class);}
 @GetMapping public Object items(Authentication a){return s.items(uid(a));}
 @PostMapping("/items") public Object add(Authentication a,@Valid @RequestBody CartItemRequest x){return s.add(uid(a),x);}
 @DeleteMapping public void clear(Authentication a){s.clear(uid(a));}
}
