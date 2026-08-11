package com.shopflow.dto;
import jakarta.validation.constraints.*; import java.util.*;
public class CommerceDtos {
 public record ProductRequest(@NotBlank String name,String description,@NotNull @DecimalMin("0") java.math.BigDecimal price,@Min(0) Integer stock,Long categoryId,Long brandId){}
 public record CartItemRequest(@NotNull Long productId,@Min(1) int quantity){}
 public record CheckoutRequest(String couponCode,@NotBlank String paymentMethod){}
 public record CouponRequest(@NotBlank String code){}
 public record ReviewRequest(@Min(1) @Max(5) int rating,String comment){}
}
