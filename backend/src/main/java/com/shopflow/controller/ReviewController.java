package com.shopflow.controller;
import com.shopflow.dto.CommerceDtos.ReviewRequest; import com.shopflow.entity.Review; import com.shopflow.repository.ReviewRepository; import jakarta.validation.Valid; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.time.OffsetDateTime;
@RestController @RequestMapping("/api/products/{productId}/reviews")
public class ReviewController {
 private final ReviewRepository repo; public ReviewController(ReviewRepository r){repo=r;}
 @PostMapping public Review create(Authentication a,@PathVariable Long productId,@Valid @RequestBody ReviewRequest x){
  var c=(io.jsonwebtoken.Claims)a.getDetails(); Review r=new Review();r.setUserId(c.get("uid",Long.class));r.setProductId(productId);r.setRating(x.rating());r.setComment(x.comment());r.setCreatedAt(OffsetDateTime.now());return repo.save(r);
 }
 @GetMapping public Object list(@PathVariable Long productId){return repo.findAll().stream().filter(x->x.getProductId().equals(productId)).toList();}
}
