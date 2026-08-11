package com.shopflow.service;
import com.shopflow.dto.CommerceDtos.CartItemRequest; import com.shopflow.entity.*; import com.shopflow.repository.*;
import org.springframework.stereotype.Service; import java.util.*;
@Service
public class CartService {
 private final CartRepository carts; private final CartItemRepository items; private final ProductRepository products;
 public CartService(CartRepository c,CartItemRepository i,ProductRepository p){carts=c;items=i;products=p;}
 public Cart getOrCreate(Long userId){return carts.findByUserId(userId).orElseGet(()->{Cart c=new Cart();c.setUserId(userId);return carts.save(c);});}
 public List<CartItem> items(Long userId){return items.findByCartId(getOrCreate(userId).getId());}
 public CartItem add(Long userId,CartItemRequest x){
  Product p=products.findById(x.productId()).orElseThrow(()->new IllegalArgumentException("Product not found"));
  if(p.getStock()<x.quantity())throw new IllegalArgumentException("Insufficient stock");
  Cart c=getOrCreate(userId); CartItem i=new CartItem();i.setCartId(c.getId());i.setProductId(p.getId());i.setQuantity(x.quantity());return items.save(i);
 }
 public void clear(Long userId){items.deleteAll(items.findByCartId(getOrCreate(userId).getId()));}
}
