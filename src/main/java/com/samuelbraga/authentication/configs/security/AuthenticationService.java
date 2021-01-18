package com.samuelbraga.authentication.configs.security;

import com.samuelbraga.authentication.exceptions.BaseException;
import com.samuelbraga.authentication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {
  private final UserRepository userRepository;

  @Autowired
  public AuthenticationService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    return this.userRepository.findByUsername(username)
      .orElseThrow(() -> new BaseException("User not found"));
  }
}
