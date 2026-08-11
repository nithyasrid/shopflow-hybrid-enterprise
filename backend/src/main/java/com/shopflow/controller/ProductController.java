package com.shopflow.controller;
import com.shopflow.dto.CommerceDtos.ProductRequest; import com.shopflow.entity.Product; import com.shopflow.service.ProductService; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/products")
public class ProductController {
 private final ProductService s; public ProductController(ProductService s){this.s=s;}
 @GetMapping public List<Product> all(){return s.all();}
 @PostMapping @PreAuthorize("hasRole('ADMIN')") public Product create(@Valid @RequestBody ProductRequest x){return s.create(x);}
 @PutMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public Product update(@PathVariable Long id,@Valid @RequestBody ProductRequest x){return s.update(id,x);}
 @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')") public void delete(@PathVariable Long id){s.delete(id);}
}
