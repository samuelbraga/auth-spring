package com.samuelbraga.authentication.services;

import org.springframework.security.core.Authentication;

public interface TokenAuthenticationService {
  String createToken(Authentication authentication);
  boolean isTokenValid(String token);
  Long getUserId(String token);
}
