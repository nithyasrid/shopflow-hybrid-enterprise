package com.shopflow.service;
import com.shopflow.dto.AuthDtos.*; import com.shopflow.entity.*; import com.shopflow.repository.*; import com.shopflow.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Service; import java.time.*; import java.util.UUID;
@Service
public class AuthService {
 private final UserRepository users; private final RefreshTokenRepository refresh; private final PasswordEncoder encoder; private final JwtService jwt;
 public AuthService(UserRepository u,RefreshTokenRepository r,PasswordEncoder e,JwtService j){users=u;refresh=r;encoder=e;jwt=j;}
 public AuthResponse register(RegisterRequest x){
  if(users.findByEmail(x.email()).isPresent()) throw new IllegalArgumentException("Email already registered");
  User u=new User();u.setName(x.name());u.setEmail(x.email());u.setPasswordHash(encoder.encode(x.password()));u.setRole("CUSTOMER");u.setCreatedAt(OffsetDateTime.now());u=users.save(u);
  return tokens(u);
 }
 public AuthResponse login(LoginRequest x){
  User u=users.findByEmail(x.email()).orElseThrow(()->new IllegalArgumentException("Invalid credentials"));
  if(!encoder.matches(x.password(),u.getPasswordHash())) throw new IllegalArgumentException("Invalid credentials");
  return tokens(u);
 }
 public AuthResponse refresh(RefreshRequest x){
  RefreshToken rt=refresh.findByToken(x.refreshToken()).orElseThrow(()->new IllegalArgumentException("Invalid refresh token"));
  if(rt.getExpiresAt().isBefore(OffsetDateTime.now())) throw new IllegalArgumentException("Refresh token expired");
  User u=users.findById(rt.getUserId()).orElseThrow(); return tokens(u);
 }
 private AuthResponse tokens(User u){
  refresh.deleteByUserId(u.getId());
  String access=jwt.generate(u.getId(),u.getEmail(),u.getRole());
  RefreshToken rt=new RefreshToken();rt.setUserId(u.getId());rt.setToken(UUID.randomUUID().toString());rt.setExpiresAt(OffsetDateTime.now().plusDays(7));refresh.save(rt);
  return new AuthResponse(access,rt.getToken(),u.getRole(),u.getId());
 }
}
