package com.samuelbraga.authentication.configs.security;

import com.samuelbraga.authentication.exceptions.BaseException;
import com.samuelbraga.authentication.models.User;
import com.samuelbraga.authentication.repositories.UserRepository;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.samuelbraga.authentication.services.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter extends OncePerRequestFilter {
  private final TokenAuthenticationService tokenAuthenticationService;
  private final UserRepository userRepository;

  @Autowired
  public AuthenticationFilter(
    TokenAuthenticationService tokenAuthenticationService,
    UserRepository userRepository
  ) {
    this.tokenAuthenticationService = tokenAuthenticationService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest httpServletRequest,
    HttpServletResponse httpServletResponse,
    FilterChain filterChain
  )
    throws ServletException, IOException {
    String token = recoveryToken(httpServletRequest);
    boolean isValidToken = tokenAuthenticationService.isTokenValid(token);

    if (isValidToken) {
      Long userId = tokenAuthenticationService.getUserId(token);

      authenticatedClient(userId);

      HttpSession session = httpServletRequest.getSession(true);
      session.setAttribute("userId", userId);
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

  private String recoveryToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");

    if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
      return null;
    }

    return token.substring(7);
  }

  private void authenticatedClient(Long userId) {
    User user =
      this.userRepository.findById(userId)
        .orElseThrow(() -> new BaseException("User not found"));
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
      user,
      null,
      user.getAuthorities()
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
