package com.shopflow.security;
import org.springframework.context.annotation.*; import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration @EnableMethodSecurity
public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean SecurityFilterChain filterChain(HttpSecurity http,JwtFilter jwt) throws Exception{
  return http.csrf(c->c.disable()).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**","/api/health").permitAll()
   .requestMatchers("/api/admin/**").hasRole("ADMIN").anyRequest().authenticated())
   .addFilterBefore(jwt,UsernamePasswordAuthenticationFilter.class).build();
 }
}
