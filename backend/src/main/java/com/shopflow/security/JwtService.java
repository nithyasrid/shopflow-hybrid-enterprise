package com.shopflow.security;
import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service;
import javax.crypto.SecretKey; import java.nio.charset.StandardCharsets; import java.time.*; import java.util.Date;
@Service
public class JwtService {
 private final SecretKey key; private final long accessMinutes;
 public JwtService(@Value("${jwt.secret}") String secret,@Value("${jwt.access-minutes}") long accessMinutes){
   key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); this.accessMinutes=accessMinutes;
 }
 public String generate(Long userId,String email,String role){
   Instant now=Instant.now();
   return Jwts.builder().subject(email).claim("uid",userId).claim("role",role)
     .issuedAt(Date.from(now)).expiration(Date.from(now.plusSeconds(accessMinutes*60)))
     .signWith(key).compact();
 }
 public Claims parse(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();}
 public boolean valid(String token){try{parse(token);return true;}catch(Exception e){return false;}}
}
