package com.samuelbraga.authentication.configs.security;

import com.samuelbraga.authentication.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TokenAuthenticationService {
  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private String expiration;

  public String createToken(Authentication authentication) {
    User user = (User) authentication.getPrincipal();

    return Jwts
      .builder()
      .setIssuer("Authentication API")
      .setSubject(user.getId().toString())
      .setIssuedAt(new Date())
      .setExpiration(
        new Date(System.currentTimeMillis() + Long.parseLong(expiration))
      )
      .signWith(SignatureAlgorithm.HS256, secret)
      .compact();
  }

  public boolean isTokenValid(String token) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

      return true;
    } catch (Exception error) {
      return false;
    }
  }

  public Long getUserId(String token) {
    Claims body = Jwts
      .parser()
      .setSigningKey(secret)
      .parseClaimsJws(token)
      .getBody();

    String id = body.getSubject();
    return Long.parseLong(id);
  }
}
