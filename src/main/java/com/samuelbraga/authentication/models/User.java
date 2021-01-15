package com.samuelbraga.authentication.models;

import com.samuelbraga.authentication.dtos.user.CreateUserDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String email;

  private String name;

  @Column(unique = true)
  private String username;

  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Profile> profiles = new ArrayList<>();

  public User(CreateUserDTO createUserDTO) {
    this.email = createUserDTO.getEmail();
    this.username = createUserDTO.getUsername();
    this.password = createUserDTO.getPassword();
    this.name = createUserDTO.getName();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return profiles;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
