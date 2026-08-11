package com.shopflow.controller;
import com.shopflow.repository.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/invoices")
public class InvoiceController {
 private final OrderRepository orders; public InvoiceController(OrderRepository o){orders=o;}
 @GetMapping("/{orderId}") public Map<String,Object> invoice(Authentication a,@PathVariable Long orderId){
  var c=(io.jsonwebtoken.Claims)a.getDetails();var o=orders.findById(orderId).orElseThrow();if(!o.getUserId().equals(c.get("uid",Long.class)))throw new org.springframework.security.access.AccessDeniedException("Forbidden");
  return Map.of("invoiceNumber","INV-"+orderId,"orderId",orderId,"subtotal",o.getSubtotal(),"discount",o.getDiscount(),"tax",o.getTax(),"shipping",o.getShipping(),"total",o.getTotalAmount(),"status","GENERATED");
 }
}
