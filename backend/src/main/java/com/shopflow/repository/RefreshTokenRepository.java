package com.shopflow.repository;
import com.shopflow.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import 
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> { Optional<RefreshToken> findByToken(String token); void deleteByUserId(Long userId); }
