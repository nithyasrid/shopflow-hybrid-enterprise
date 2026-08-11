package com.shopflow.repository;
import com.shopflow.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import 
public interface CouponRepository extends JpaRepository<Coupon,Long> { Optional<Coupon> findByCode(String code); }
