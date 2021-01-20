package com.samuelbraga.authentication.services.impl;

import com.samuelbraga.authentication.exceptions.BaseException;
import com.samuelbraga.authentication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Autowired
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    return this.userRepository.findByUsername(username)
      .orElseThrow(() -> new BaseException("User not found"));
  }
}
