package com.shopflow.dto;
import jakarta.validation.constraints.*;
public class AuthDtos {
 public record RegisterRequest(@NotBlank String name,@Email @NotBlank String email,@Size(min=8) String password,String role){}
 public record LoginRequest(@Email @NotBlank String email,@NotBlank String password){}
 public record RefreshRequest(@NotBlank String refreshToken){}
 public record AuthResponse(String accessToken,String refreshToken,String role,Long userId){}
}
