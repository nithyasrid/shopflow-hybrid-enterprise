package com.shopflow.service;
import com.fasterxml.jackson.databind.ObjectMapper; import com.shopflow.dto.CommerceDtos.CheckoutRequest; import com.shopflow.entity.*; import com.shopflow.repository.*;
import org.springframework.kafka.core.KafkaTemplate; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal; import java.time.OffsetDateTime; import java.util.*;
@Service
public class OrderService {
 private final CartService cart; private final CartItemRepository cartItems; private final ProductRepository products; private final OrderRepository orders; private final OrderItemRepository orderItems; private final PaymentRepository payments; private final CouponRepository coupons; private final KafkaTemplate<String,String> kafka; private final ObjectMapper mapper=new ObjectMapper().findAndRegisterModules();
 public OrderService(CartService c,CartItemRepository ci,ProductRepository p,OrderRepository o,OrderItemRepository oi,PaymentRepository pay,CouponRepository cp,KafkaTemplate<String,String> k){cart=c;cartItems=ci;products=p;orders=o;orderItems=oi;payments=pay;coupons=cp;kafka=k;}
 @Transactional
 public Map<String,Object> checkout(Long userId,CheckoutRequest x){
  var cis=cartItems.findByCartId(cart.getOrCreate(userId).getId()); if(cis.isEmpty())throw new IllegalArgumentException("Cart is empty");
  BigDecimal subtotal=BigDecimal.ZERO;
  for(var ci:cis){var p=products.findById(ci.getProductId()).orElseThrow(); if(p.getStock()<ci.getQuantity())throw new IllegalArgumentException("Insufficient stock for "+p.getName()); subtotal=subtotal.add(p.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));}
  BigDecimal discount=BigDecimal.ZERO;
  if(x.couponCode()!=null&&!x.couponCode().isBlank()){var cp=coupons.findByCode(x.couponCode()).orElseThrow(()->new IllegalArgumentException("Invalid coupon"));if(cp.getExpiresAt().isBefore(OffsetDateTime.now()))throw new IllegalArgumentException("Coupon expired");if(cp.getUsedCount()>=cp.getUsageLimit())throw new IllegalArgumentException("Coupon usage limit reached");if(subtotal.compareTo(cp.getMinimumCartValue())<0)throw new IllegalArgumentException("Minimum cart value not reached");discount=cp.getDiscountType().equals("PERCENTAGE")?subtotal.multiply(cp.getDiscountValue()).divide(BigDecimal.valueOf(100)):cp.getDiscountValue();cp.setUsedCount(cp.getUsedCount()+1);coupons.save(cp);}
  BigDecimal tax=subtotal.subtract(discount).multiply(new BigDecimal("0.18")); BigDecimal shipping=subtotal.compareTo(BigDecimal.valueOf(2000))>=0?BigDecimal.ZERO:BigDecimal.valueOf(99); BigDecimal total=subtotal.subtract(discount).add(tax).add(shipping);
  Order o=new Order();o.setUserId(userId);o.setSubtotal(subtotal);o.setDiscount(discount);o.setTax(tax);o.setShipping(shipping);o.setTotalAmount(total);o.setStatus("CONFIRMED");o.setCreatedAt(OffsetDateTime.now());o=orders.save(o);
  for(var ci:cis){var p=products.findById(ci.getProductId()).orElseThrow();OrderItem oi=new OrderItem();oi.setOrderId(o.getId());oi.setProductId(p.getId());oi.setQuantity(ci.getQuantity());oi.setUnitPrice(p.getPrice());orderItems.save(oi);p.setStock(p.getStock()-ci.getQuantity());products.save(p);}
  Payment pay=new Payment();pay.setOrderId(o.getId());pay.setAmount(total);pay.setMethod(x.paymentMethod());pay.setStatus("SUCCESS");pay.setCreatedAt(OffsetDateTime.now());payments.save(pay);
  cart.clear(userId); publish("shopflow.orders",Map.of("eventType","ORDER_CREATED","orderId",o.getId(),"userId",userId,"amount",total,"timestamp",OffsetDateTime.now().toString()));
  publish("shopflow.payments",Map.of("eventType","PAYMENT_COMPLETED","orderId",o.getId(),"userId",userId,"amount",total,"timestamp",OffsetDateTime.now().toString()));
  return Map.of("orderId",o.getId(),"subtotal",subtotal,"discount",discount,"tax",tax,"shipping",shipping,"total",total,"status",o.getStatus());
 }
 private void publish(String topic,Object payload){try{kafka.send(topic,UUID.randomUUID().toString(),mapper.writeValueAsString(payload));}catch(Exception e){throw new IllegalStateException("Kafka publish failed",e);}}
}
