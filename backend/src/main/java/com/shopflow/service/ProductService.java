package com.shopflow.service;
import com.shopflow.dto.CommerceDtos.ProductRequest; import com.shopflow.entity.Product; import com.shopflow.repository.ProductRepository;
import org.springframework.stereotype.Service; import java.time.OffsetDateTime; import java.util.List;
@Service
public class ProductService {
 private final ProductRepository repo; public ProductService(ProductRepository r){repo=r;}
 public List<Product> all(){return repo.findAll();}
 public Product create(ProductRequest x){Product p=new Product();map(p,x);p.setCreatedAt(OffsetDateTime.now());return repo.save(p);}
 public Product update(Long id,ProductRequest x){Product p=repo.findById(id).orElseThrow(()->new IllegalArgumentException("Product not found"));map(p,x);return repo.save(p);}
 public void delete(Long id){Product p=repo.findById(id).orElseThrow();p.setActive(false);repo.save(p);}
 private void map(Product p,ProductRequest x){p.setName(x.name());p.setDescription(x.description());p.setPrice(x.price());p.setStock(x.stock());p.setCategoryId(x.categoryId());p.setBrandId(x.brandId());}
}
