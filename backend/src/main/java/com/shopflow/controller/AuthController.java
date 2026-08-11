package com.shopflow.controller;
import com.shopflow.dto.AuthDtos.*; import com.shopflow.service.AuthService; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final AuthService service; public AuthController(AuthService s){service=s;}
 @PostMapping("/register") public AuthResponse register(@Valid @RequestBody RegisterRequest x){return service.register(x);}
 @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest x){return service.login(x);}
 @PostMapping("/refresh") public AuthResponse refresh(@Valid @RequestBody RefreshRequest x){return service.refresh(x);}
}
