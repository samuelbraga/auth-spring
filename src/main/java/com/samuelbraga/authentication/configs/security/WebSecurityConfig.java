package com.samuelbraga.authentication.configs.security;

import com.samuelbraga.authentication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private final AuthenticationService authenticationService;
  private final TokenAuthenticationService tokenAuthenticationService;
  private final UserRepository userRepository;

  @Autowired
  public WebSecurityConfig(
    AuthenticationService authenticationService,
    TokenAuthenticationService tokenAuthenticationService,
    UserRepository userRepository
  ) {
    this.authenticationService = authenticationService;
    this.tokenAuthenticationService = tokenAuthenticationService;
    this.userRepository = userRepository;
  }

  @Override
  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .userDetailsService(this.authenticationService)
      .passwordEncoder(new BCryptPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers("/console/**")
      .permitAll()
      .antMatchers(HttpMethod.POST, "/users")
      .permitAll()
      .antMatchers(HttpMethod.POST, "/auth")
      .permitAll()
      .antMatchers(HttpMethod.GET, "/users")
      .hasAuthority("ADMIN")
      .anyRequest()
      .authenticated();

    http
      .csrf()
      .disable()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.headers().frameOptions().disable();
    http.addFilterBefore(
      new AuthenticationFilter(tokenAuthenticationService, userRepository),
      UsernamePasswordAuthenticationFilter.class
    );
  }
}
