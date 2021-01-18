package com.samuelbraga.authentication.services.impl;

import com.samuelbraga.authentication.configs.security.TokenAuthenticationService;
import com.samuelbraga.authentication.dtos.authenticate.CredentialsDTO;
import com.samuelbraga.authentication.dtos.authenticate.TokenDTO;
import com.samuelbraga.authentication.exceptions.BaseException;
import com.samuelbraga.authentication.services.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {
  private final AuthenticationManager authenticationManager;
  private final TokenAuthenticationService tokenAuthenticationService;

  @Autowired
  public AuthenticateServiceImpl(
    AuthenticationManager authenticationManager,
    TokenAuthenticationService tokenAuthenticationService
  ) {
    this.authenticationManager = authenticationManager;
    this.tokenAuthenticationService = tokenAuthenticationService;
  }

  @Override
  public TokenDTO authenticate(CredentialsDTO credentialsDTO) {
    UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(
      credentialsDTO.getUsername(),
      credentialsDTO.getPassword()
    );

    try {
      Authentication authentication = authenticationManager.authenticate(
        userAuth
      );

      String token = tokenAuthenticationService.createToken(authentication);

      return new TokenDTO(token, "Bearer");
    } catch (AuthenticationException error) {
      throw new BaseException("User or password incorrect");
    }
  }
}
