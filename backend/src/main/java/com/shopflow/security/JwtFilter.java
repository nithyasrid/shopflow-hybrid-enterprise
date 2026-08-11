package com.shopflow.security;
import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter;
@Component
public class JwtFilter extends OncePerRequestFilter {
 private final JwtService jwt; public JwtFilter(JwtService jwt){this.jwt=jwt;}
 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,java.io.IOException{
  String h=req.getHeader("Authorization");
  if(h!=null&&h.startsWith("Bearer ")){String t=h.substring(7); if(jwt.valid(t)){var c=jwt.parse(t); String role=c.get("role",String.class);
    var auth=new UsernamePasswordAuthenticationToken(c.getSubject(),null,java.util.List.of(new SimpleGrantedAuthority("ROLE_"+role)));
    auth.setDetails(c); SecurityContextHolder.getContext().setAuthentication(auth);}}
  chain.doFilter(req,res);
 }
}
